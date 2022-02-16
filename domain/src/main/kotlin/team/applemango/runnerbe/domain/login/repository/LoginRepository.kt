/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepository.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.login.repository

import team.applemango.runnerbe.domain.login.model.AccessToken
import team.applemango.runnerbe.domain.login.model.UserToken

interface LoginRepository {
    /**
     * 로그인 요청 쿼리 (SNS 로그인)
     */
    suspend fun request(platformName: String, accessToken: AccessToken): UserToken
}
