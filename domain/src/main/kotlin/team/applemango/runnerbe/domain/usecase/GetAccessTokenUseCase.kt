/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GetKakaoAccessTokenUseCase.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLoginUseCase.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.usecase

import team.applemango.runnerbe.domain.repository.AccessTokenRepository

class GetAccessTokenUseCase(private val repo: AccessTokenRepository) {
    suspend operator fun invoke() = runCatching {
        repo.getAccessToken()
    }
}
