/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLoginUseCase.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.usecase

import team.applemango.runnerbe.domain.register.runnerbe.repository.AccessTokenRepository

class GetKakaoAccessTokenUseCase(private val repo: AccessTokenRepository) {
    suspend operator fun invoke() = runCatching {
        repo.getKakao()
    }
}
