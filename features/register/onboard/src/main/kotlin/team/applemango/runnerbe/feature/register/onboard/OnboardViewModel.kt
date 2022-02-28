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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.random.Random
import kotlinx.coroutines.cancel
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
import team.applemango.runnerbe.domain.register.runnerbe.model.UserRegister
import team.applemango.runnerbe.domain.register.runnerbe.constant.UserRegisterResult
import team.applemango.runnerbe.domain.register.runnerbe.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.domain.register.runnerbe.usecase.UserRegisterUseCase
import team.applemango.runnerbe.domain.register.mailjet.usecase.MailjetSendUseCase
import team.applemango.runnerbe.feature.register.onboard.constant.FirebaseStoragePath
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterSideEffect
import team.applemango.runnerbe.shared.base.BaseViewModel
import team.applemango.runnerbe.shared.constant.DataStoreKey

private val SendEmailExceptionWithNoMessage =
    Exception("user.sendEmailVerification is fail. But, exception message is null.")
private val UserNullException =
    Exception("Firebase.auth.createUserWithEmailAndPassword success. But, current user is null.")
private val ImageUpdateExceptionWithNull =
    Exception("Image upload is fail. But, exception is null.")

// TODO: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/38
internal class OnboardViewModel @Inject constructor(
    private val checkUsableEmailUseCase: CheckUsableEmailUseCase,
    private val userRegisterUseCase: UserRegisterUseCase,
    private val mailjetSendUseCase: MailjetSendUseCase,
) : BaseViewModel(), ContainerHost<RegisterState, RegisterSideEffect> {

    override val container = container<RegisterState, RegisterSideEffect>(RegisterState.None)

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

    // 이메일 인증하는 step 에서 따로 label 이 있어서 listener 콜백 처리
    fun sendVerifyMail(
        email: String,
        onSuccess: () -> Unit,
        onException: (Throwable) -> Unit,
    ) = viewModelScope.launch {
        val token = Random.nextInt().toString() // TODO: 토큰 인증
        mailjetSendUseCase(token = token, email = email)
            .onSuccess { result ->
                when (result.isSuccess) {
                    true -> {
                        onSuccess()
                    }
                    else -> {
                        onException(Exception(result.errorResult?.errorMessage))
                    }
                }
            }
            .onFailure { exception ->
                onException(exception)
            }
    }

    // register entry point
    fun registerUser(
        dataStore: DataStore<Preferences>,
        photo: Bitmap?,
        nextStep: Step,
    ) = intent {
        // 회원가입 응답이 평균 0.03초 안에 와서 로딩 삭제
        /*reduce {
            RegisterState.Request
        }*/
        viewModelScope.launch {
            dataStore.data.cancellable().collect { preferences ->
                val uuid = preferences[DataStoreKey.Login.Uuid]
                val year = preferences[DataStoreKey.Onboard.Year]
                val gender = preferences[DataStoreKey.Onboard.Gender]
                val job = preferences[DataStoreKey.Onboard.Job]
                // StateFlow 로 저장되는 값이라 TextField 의 초기값인 "" (공백) 이 들어갈 수 있음
                val officeEmail = preferences[DataStoreKey.Onboard.Email]?.ifEmpty { null }
                if (listOf(uuid, year, gender, job).contains(null)) {
                    reduce {
                        RegisterState.NullInformation
                    }
                } else {
                    var photoUrl: String? = null
                    val genderCode = Gender.values().first { it.string == gender!! }.code
                    if (photo != null) { // 사원증을 통한 인증일 경우
                        reduce {
                            RegisterState.ImageUploading
                        }
                        photoUrl = uploadImage(photo, uuid!!) ?: run {
                            // uploadImage 내부에서 emitException 해주고 있음
                            cancel("user login data collect and register execute must be once.")
                            return@collect
                        }
                    }
                    val user = UserRegister(
                        uuid = uuid!!,
                        birthday = year!!,
                        gender = genderCode,
                        job = job!!,
                        officeEmail = officeEmail, // nullable
                        idCardImageUrl = photoUrl // nullable
                    )
                    requestUserRegister(user, nextStep)
                }
                cancel("user login data collect and register execute must be once.")
            }
        }
    }

    /**
     * 매우 좋지 않은 방식
     * TODO: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/36
     *
     * @return 성공시 이미지 주소, 실패시 null
     */
    private suspend fun uploadImage(photo: Bitmap, userUuid: String): String? =
        suspendCancellableCoroutine { continuation ->
            val baos = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            storageRef.child(FirebaseStoragePath).child(userUuid).run {
                putBytes(data)
                    .continueWithTask { downloadUrl }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            continuation.resume(task.result.toString())
                        } else {
                            emitException(task.exception ?: ImageUpdateExceptionWithNull)
                            continuation.resume(null)
                        }
                    }
            }
        }

    private fun requestUserRegister(user: UserRegister, nextStep: Step) = intent {
        userRegisterUseCase(user)
            .onSuccess { result ->
                when (result) {
                    is UserRegisterResult.Success -> {
                        val registerState = if (user.isVerifyWithEmployeeId) {
                            RegisterState.VerifyRequestDone // 가입 신청 (사원증 인증 대기중)
                        } else {
                            RegisterState.RegisterDone // 가입 완료
                        }
                        reduce {
                            registerState
                        }
                        postSideEffect(RegisterSideEffect.SaveUserJwt(result.jwt))
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
