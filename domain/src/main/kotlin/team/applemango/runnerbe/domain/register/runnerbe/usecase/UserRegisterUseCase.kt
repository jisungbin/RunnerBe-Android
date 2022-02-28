/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRegisterUseCase.kt] created by Ji Sungbin on 22. 2. 12. 오후 3:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.usecase

import team.applemango.runnerbe.domain.register.runnerbe.model.UserRegister
import team.applemango.runnerbe.domain.register.runnerbe.repository.RegisterRepository

class UserRegisterUseCase(private val repo: RegisterRepository) {
    suspend operator fun invoke(user: UserRegister) = runCatching {
        repo.register(user)
    }
}
