/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepository.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.login.repository

import team.applemango.runnerbe.domain.register.login.model.AccessToken
import team.applemango.runnerbe.domain.register.login.model.UserToken

interface LoginRepository {
    /**
     * 로그인 요청 쿼리 (SNS 로그인)
     *
     * @param platformName 플랫폼 타입 (카카오 (1번 API), 네이버 (2번 API), 애플)
     * @param accessToken 액세스 토큰 객체, API Call 할 때 Body 로 들어가서 data class 로 해야 함
     */
    suspend fun request(platformName: String, accessToken: AccessToken): UserToken
}
