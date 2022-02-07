/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginViewModel.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin

import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.login.constant.PlatformType
import team.applemango.runnerbe.domain.login.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.login.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.domain.login.usecase.LoginUseCase
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginSideEffect
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginState
import team.applemango.runnerbe.shared.base.BaseViewModel
import javax.inject.Inject

internal class SnsLoginViewModel @Inject constructor(
    private val getKakaoKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
    private val getNaverKakaoAccessTokenUseCase: GetNaverAccessTokenUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState(false))

    fun login(platformType: PlatformType) = intent {
        when (platformType) {
            PlatformType.Kakao -> getKakaoKakaoAccessTokenUseCase()
            PlatformType.Naver -> getNaverKakaoAccessTokenUseCase()
            else -> throw NotImplementedError()
        }.onSuccess { token ->
            loginUseCase(platformType, token)
                .onSuccess { user ->
                    reduce {
                        LoginState(true)
                    }
                    postSideEffect(LoginSideEffect.SaveUuid(user.uuid!!)) // must NonNull
                }.onFailure { throwable ->
                    emitException(throwable)
                }
        }.onFailure { throwable ->
            emitException(throwable)
        }
    }
}
