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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.login.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.feature.register.onboard.constant.ApplicationMinSdkLevel
import team.applemango.runnerbe.feature.register.onboard.constant.EmailVerifyCode
import team.applemango.runnerbe.feature.register.onboard.constant.FirebaseStoragePath
import team.applemango.runnerbe.feature.register.onboard.constant.PresentationPackage
import team.applemango.runnerbe.domain.login.model.UserRegister
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterSideEffect
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterState
import team.applemango.runnerbe.shared.base.BaseViewModel

internal class OnboardViewModel @Inject constructor(
    private val checkUsableEmailUseCase: CheckUsableEmailUseCase,
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

    fun register(userRegister: UserRegister, photo: Bitmap?) = intent {
        reduce {
            RegisterState.Request
        }
        var photoUrl: String? = null
        if (photo != null) {
            photoUrl = uploadImage(photo, userRegister.uuid)
            if (photoUrl == null) { // null 이면 내부에서 emitException 함
                reduce {
                    RegisterState.None
                }
                return@intent
            }
        }

    }
}
