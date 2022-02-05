/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginViewModel.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin

import dagger.hilt.android.lifecycle.HiltViewModel
import team.applemango.runnerbe.feature.register.snslogin.usecase.KakaoLoginUseCase
import team.applemango.runnerbe.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class SnsLoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
) : BaseViewModel()
