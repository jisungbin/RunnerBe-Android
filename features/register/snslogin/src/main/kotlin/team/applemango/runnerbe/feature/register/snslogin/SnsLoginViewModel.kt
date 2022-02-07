/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginViewModel.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.login.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.login.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.feature.register.snslogin.constant.LoginType
import team.applemango.runnerbe.shared.base.BaseViewModel
import javax.inject.Inject

internal class SnsLoginViewModel @Inject constructor(
    private val getKakaoKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
    private val getNaverKakaoAccessTokenUseCase: GetNaverAccessTokenUseCase,
) : BaseViewModel() {

    private val _accessToken = MutableSharedFlow<String>()
    val accessToken = _accessToken.asSharedFlow()

    fun getAccessToken(loginType: LoginType) = viewModelScope.launch {
        when (loginType) {
            LoginType.Kakao -> getKakaoKakaoAccessTokenUseCase()
            LoginType.Naver -> getNaverKakaoAccessTokenUseCase()
            LoginType.Apple -> throw NotImplementedError()
        }.onSuccess { token ->
            _accessToken.emit(token)
        }.onFailure { throwable ->
            emitException(throwable)
        }
    }
}
