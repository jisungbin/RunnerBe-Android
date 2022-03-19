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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.firebase.usecase.ImageUploadUseCase
import team.applemango.runnerbe.domain.register.mailjet.usecase.MailjetSendUseCase
import team.applemango.runnerbe.domain.register.runnerbe.constant.UserRegisterResult
import team.applemango.runnerbe.domain.register.runnerbe.model.UserRegister
import team.applemango.runnerbe.domain.register.runnerbe.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.domain.register.runnerbe.usecase.UserRegisterUseCase
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterSideEffect
import team.applemango.runnerbe.shared.android.base.BaseViewModel
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.domain.util.flowExceptionMessage
import kotlin.random.Random

private val SendEmailExceptionWithNoMessage =
    Exception("user.sendEmailVerification is fail. But, exception message is null.")
private val UserNullException =
    Exception("Firebase.auth.createUserWithEmailAndPassword success. But, current user is null.")
private const val DefaultEmployeeIdImagePath = "register-employee-id.jpg"

// TODO: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/38
internal class OnboardViewModel(
    private val checkUsableEmailUseCase: CheckUsableEmailUseCase,
    private val userRegisterUseCase: UserRegisterUseCase,
    private val mailjetSendUseCase: MailjetSendUseCase,
    private val imageUploadUseCase: ImageUploadUseCase,
) : BaseViewModel(), ContainerHost<RegisterState, RegisterSideEffect> {

    override val container = container<RegisterState, RegisterSideEffect>(RegisterState.None)

    private val _emailVerifyStateFlow = MutableStateFlow(false)
    val emailVerifyStateFlow = _emailVerifyStateFlow.asStateFlow()

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
            dataStore
                .data
                .cancellable()
                .catch { exception ->
                    emitException(
                        Exception(
                            flowExceptionMessage(
                                flowName = "OnboardViewModel DataStore",
                                exception = exception
                            )
                        )
                    )
                }
                .collect { preferences ->
                    val uuid = preferences[DataStoreKey.Login.Uuid]
                    val year = preferences[DataStoreKey.Onboard.Year]
                    val gender = preferences[DataStoreKey.Onboard.Gender]
                    val job = preferences[DataStoreKey.Onboard.Job]
                    // StateFlow 로 저장되는 값이라 TextField 의 초기값인 "" (공백) 이 들어갈 수 있음
                    val officeEmail = preferences[DataStoreKey.Onboard.Email]?.ifEmpty { null }
                    if (uuid == null || year == null || gender == null || job == null) {
                        reduce {
                            RegisterState.NullInformation
                        }
                    } else {
                        var photoUrl: String? = null
                        val genderCode = Gender.values().first { it.string == gender }.code
                        if (photo != null) { // 사원증을 통한 인증일 경우
                            reduce {
                                RegisterState.ImageUploading
                            }
                            photoUrl = imageUploadUseCase(
                                image = photo,
                                path = DefaultEmployeeIdImagePath,
                                userId = Random.nextInt()
                            ).getOrElse { exception ->
                                emitException(exception)
                                reduce {
                                    RegisterState.ImageUploadError
                                }
                                return@collect
                            }
                        }
                        val user = UserRegister(
                            uuid = uuid,
                            birthday = year,
                            gender = genderCode,
                            job = job,
                            officeEmail = officeEmail, // nullable
                            idCardImageUrl = photoUrl // nullable
                        )
                        requestUserRegister(
                            user = user,
                            nextStep = nextStep
                        )
                    }
                    cancel("user login data collect and register execute must be once.")
                }
        }
    }

    private fun requestUserRegister(
        user: UserRegister,
        nextStep: Step,
    ) = intent {
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
