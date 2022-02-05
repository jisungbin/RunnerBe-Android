/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GetNaverAccessTokenUseCase.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:18
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.usecase

import team.applemango.runnerbe.domain.repository.NaverLoginRepository

class GetNaverAccessTokenUseCase(private val repo: NaverLoginRepository) {
    suspend operator fun invoke() = runCatching {
        repo.getAccessToken()
    }
}
