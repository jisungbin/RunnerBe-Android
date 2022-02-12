/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardViewModel.kt] created by Ji Sungbin on 22. 2. 11. 오전 4:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard

import android.graphics.Bitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.login.model.UserRegister
import team.applemango.runnerbe.domain.login.model.result.UserRegisterResult
import team.applemango.runnerbe.domain.login.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.domain.login.usecase.UserRegisterUseCase
import team.applemango.runnerbe.feature.register.onboard.constant.ApplicationMinSdkLevel
import team.applemango.runnerbe.feature.register.onboard.constant.EmailVerifyCode
import team.applemango.runnerbe.feature.register.onboard.constant.FirebaseStoragePath
import team.applemango.runnerbe.feature.register.onboard.constant.Gender
import team.applemango.runnerbe.feature.register.onboard.constant.PresentationPackage
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterSideEffect
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterState
import team.applemango.runnerbe.shared.base.BaseViewModel
import team.applemango.runnerbe.shared.constant.DataStoreKey

internal class OnboardViewModel @Inject constructor(
    private val checkUsableEmailUseCase: CheckUsableEmailUseCase,
    private val userRegisterUseCase: UserRegisterUseCase,
) : BaseViewModel(), ContainerHost<RegisterState, RegisterSideEffect> {

    override val container = container<RegisterState, RegisterSideEffect>(RegisterState.None)

    private val alphabetRange = ('a'..'z') + ('A'..'Z') + (0..10)
    private val randomPassword get() = List(10) { alphabetRange.random() }.joinToString("")

    private val sendEmailExceptionWithNoMessage =
        Exception("user.sendEmailVerification is fail. But, exception message is null.")
    private val userNullException =
        Exception("Firebase.auth.createUserWithEmailAndPassword success. But, current user is null.")
    private val imageUpdateException = Exception("Image upload is fail. But, exception is null.")

    private val _emailVerifyStateFlow = MutableStateFlow(false)
    val emailVerifyStateFlow = _emailVerifyStateFlow.asStateFlow()

    private val storageRef by lazy { Firebase.storage.reference }

    suspend fun checkUsableEmail(email: String) =
        checkUsableEmailUseCase(email).getOrElse { exception ->
            emitException(exception)
            false
        }

    fun updateEmailVerifyState(state: Boolean) = viewModelScope.launch {
        _emailVerifyStateFlow.value = state
    }

    fun createUserWithEmailVerify(
        email: String,
        emailSendSuccess: () -> Unit,
        exceptionHandler: (Exception) -> Unit,
        // 사용하는 step ui 에 state 를 보여주는 label 을 따로 갖고 있기 때문에 exception handle 별도 ui 처리
    ) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, randomPassword)
            .addOnSuccessListener {
                Firebase.auth.currentUser?.let { user ->
                    user.sendEmailVerification(
                        actionCodeSettings {
                            url = "https://runnerbe-auth.shop/$EmailVerifyCode"
                            handleCodeInApp = true // 필수!
                            setAndroidPackageName(
                                PresentationPackage, // 리다이렉트될 앱 패키지명
                                true, // 이용 불가능시 플레이스토어 이동해서 설치 요청
                                ApplicationMinSdkLevel // min sdk level
                            )
                        }
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emailSendSuccess()
                        } else {
                            exceptionHandler(task.exception ?: sendEmailExceptionWithNoMessage)
                        }
                        // 이 메일로 끝까지 회원가입 안 했을떈 메일 다시 쓸 수 있게 하기 위해 유저 삭제
                        // 러너비 서버에서 따로 이메일 중복 검사 절차 거침
                        user.delete()
                            .addOnSuccessListener {
                                logeukes { "$email 유저 삭제 완료" }
                            }.addOnFailureListener { exception ->
                                logeukes(type = LoggerType.E) { "$email 유저 삭제 실패: $exception" }
                            }
                    }
                } ?: run {
                    exceptionHandler(userNullException)
                }
            }
            .addOnFailureListener { exception ->
                exceptionHandler(exception)
            }
    }

    /**
     * @return 성공시 이미지 주소, 실패시 null
     */
    private suspend fun uploadImage(photo: Bitmap, userUuid: String): String? =
        suspendCancellableCoroutine { continuation ->
            val baos = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray() // TODO: 메모리 사용에 안 좋은 방식, 개선 필요
            storageRef.child(FirebaseStoragePath).child(userUuid).run {
                putBytes(data)
                    .continueWithTask { downloadUrl }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            continuation.resume(task.result.toString())
                        } else {
                            emitException(task.exception ?: imageUpdateException)
                            continuation.resume(null)
                        }
                    }
            }
        }

    fun register(dataStore: DataStore<Preferences>, photo: Bitmap?, nextStep: Step) = intent {
        reduce {
            RegisterState.Request
        }
        coroutineScope {
            dataStore.data.cancellable().collect { preferences ->
                val uuid = preferences[DataStoreKey.Login.Uuid]
                val year = preferences[DataStoreKey.Onboard.Year]
                val gender = preferences[DataStoreKey.Onboard.Gender]
                val job = preferences[DataStoreKey.Onboard.Job]
                // StateFlow 로 저장되는 값이라 TextField 의 초기값인 "" 이 들어갈 수 있음
                val officeEmail = preferences[DataStoreKey.Onboard.Email]?.ifEmpty { null }
                logeukes { listOf(uuid, year, gender, job, officeEmail) }
                if (listOf(uuid, year, gender, job, officeEmail).contains(null)) {
                    reduce {
                        RegisterState.NullInformation
                    }
                    postSideEffect(RegisterSideEffect.ResetStep)
                } else {
                    var photoUrl: String? = null
                    if (photo != null) { // 사원증을 통한 인증일 경우
                        photoUrl = uploadImage(photo, uuid!!)
                        if (photoUrl == null) { // null 이면 내부에서 emitException 함
                            reduce {
                                RegisterState.None // uploadImage 내부에서 emitException 해주고 있음
                            }
                            return@collect
                        }
                    }
                    val user = UserRegister(
                        uuid = uuid!!,
                        birthday = year!!,
                        gender = Gender.values().first { it.string == gender!! }.code,
                        job = job!!,
                        officeEmail = officeEmail, // nullable
                        idCardImageUrl = photoUrl // nullable
                    )
                    userRegisterUseCase(user)
                        .onSuccess { result ->
                            when (result) {
                                UserRegisterResult.Success -> {
                                    reduce {
                                        RegisterState.Success
                                    }
                                    postSideEffect(RegisterSideEffect.NavigateToNextStep(nextStep))
                                }
                                UserRegisterResult.DuplicateUuid -> {
                                    reduce {
                                        RegisterState.DuplicateUuid
                                    }
                                }
                                UserRegisterResult.DuplicateNickname -> {
                                    reduce {
                                        RegisterState.DuplicateNickname
                                    }
                                }
                                UserRegisterResult.DuplicateEmail -> {
                                    reduce {
                                        RegisterState.DuplicateEmail
                                    }
                                }
                                UserRegisterResult.DatabaseError -> {
                                    reduce {
                                        RegisterState.DatabaseError
                                    }
                                }
                                is UserRegisterResult.Exception -> {
                                    emitException(Exception("Server request fail: ${result.code}"))
                                    reduce {
                                        RegisterState.None
                                    }
                                }
                            }
                        }
                        .onFailure { exception ->
                            emitException(exception)
                            reduce {
                                RegisterState.None
                            }
                        }
                }
            }
            cancel("user login data collect and register execute must be once.")
        }
    }
}
