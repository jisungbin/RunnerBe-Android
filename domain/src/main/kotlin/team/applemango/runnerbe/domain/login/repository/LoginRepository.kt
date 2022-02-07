/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepository.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.login.repository

import team.applemango.runnerbe.domain.login.constant.PlatformType
import team.applemango.runnerbe.domain.login.model.User

interface LoginRepository {
    suspend fun request(platformType: PlatformType, accessToken: String): User
}
