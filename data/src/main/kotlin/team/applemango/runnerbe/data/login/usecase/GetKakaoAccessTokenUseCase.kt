/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLoginUseCase.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.usecase

import android.content.Context
import dagger.hilt.android.scopes.ViewModelScoped
import team.applemango.runnerbe.data.login.repository.KakaoLogin
import javax.inject.Inject

@ViewModelScoped
internal class GetKakaoAccessTokenUseCase @Inject constructor(private val kakaoLogin: KakaoLogin) {
    suspend operator fun invoke(context: Context) = runCatching {
        kakaoLogin(context)
    }
}
