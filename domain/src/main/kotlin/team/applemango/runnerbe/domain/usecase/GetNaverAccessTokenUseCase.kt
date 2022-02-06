/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GetNaverAccessTokenUseCase.kt] created by Ji Sungbin on 22. 2. 6. 오후 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.usecase

import team.applemango.runnerbe.domain.repository.AccessTokenRepository

class GetNaverAccessTokenUseCase(private val repo: AccessTokenRepository) {
    suspend operator fun invoke() = runCatching {
        repo.getNaver()
    }
}
