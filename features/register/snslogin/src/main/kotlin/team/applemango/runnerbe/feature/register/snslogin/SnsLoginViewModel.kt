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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class SnsLoginViewModel @Inject constructor(
    private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
    private val getNaverAccessTokenUseCase: GetNaverAccessTokenUseCase,
) : BaseViewModel() {

    private val _accessToken = MutableSharedFlow<String>()
    val accessToken = _accessToken.asSharedFlow()

    fun getKakaoAccessToken() = viewModelScope.launch {
        getKakaoAccessTokenUseCase()
            .onSuccess { token ->
                _accessToken.emit(token)
            }
            .onFailure { throwable ->
                emitException(throwable)
            }
    }
}
