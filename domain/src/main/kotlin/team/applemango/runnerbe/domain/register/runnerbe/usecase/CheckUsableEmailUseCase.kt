/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [CheckEmailDuplicateUseCase.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.usecase

import team.applemango.runnerbe.domain.register.runnerbe.repository.RegisterRepository

class CheckUsableEmailUseCase(private val repo: RegisterRepository) {
    suspend operator fun invoke(email: String) = runCatching {
        repo.checkUsableEmail(email)
    }
}
