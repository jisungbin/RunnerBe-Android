/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginViewModel.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.register.login.constant.PlatformType
import team.applemango.runnerbe.domain.register.login.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.register.login.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.domain.register.login.usecase.LoginUseCase
import team.applemango.runnerbe.feature.register.snslogin.constant.LoginState
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginSideEffect
import team.applemango.runnerbe.shared.base.BaseViewModel

internal class SnsLoginViewModel @Inject constructor(
    private val getKakaoKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
    private val getNaverKakaoAccessTokenUseCase: GetNaverAccessTokenUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState.None)

    fun login(platformType: PlatformType) = intent {
        withContext(Dispatchers.Main) { // 내부적으로 다이얼로그를 띄우는 로직이 있어서 UI Thread 에서 돌림
            when (platformType) {
                PlatformType.Kakao -> getKakaoKakaoAccessTokenUseCase()
                PlatformType.Naver -> getNaverKakaoAccessTokenUseCase()
                else -> throw NotImplementedError()
            }.onSuccess { token ->
                loginUseCase(platformType = platformType, accessToken = token)
                    .onSuccess { user ->
                        if (user.isAlreadyRegisterUser) { // 이미 가입후 회원가입까지 모두 마친 유저
                            postSideEffect(LoginSideEffect.SaveJwt(user.jwt!!)) // must NonNull
                            reduce {
                                LoginState.Registered
                            }
                        } else {
                            postSideEffect(LoginSideEffect.SaveUuid(user.uuid!!)) // must NonNull
                            reduce {
                                LoginState.Done
                            }
                        }
                    }.onFailure { throwable ->
                        emitException(throwable)
                    }
            }.onFailure { throwable ->
                emitException(throwable)
            }
        }
    }
}
