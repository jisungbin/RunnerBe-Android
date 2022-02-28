/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginUseCase.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:18
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.usecase

import team.applemango.runnerbe.domain.register.runnerbe.constant.PlatformType
import team.applemango.runnerbe.domain.register.runnerbe.model.AccessToken
import team.applemango.runnerbe.domain.register.runnerbe.repository.RegisterRepository

class LoginUseCase(private val repo: RegisterRepository) {
    suspend operator fun invoke(platformType: PlatformType, accessToken: String) = runCatching {
        repo.login(
            platformName = platformType.name.lowercase(),
            accessToken = AccessToken(accessToken)
        )
    }
}
