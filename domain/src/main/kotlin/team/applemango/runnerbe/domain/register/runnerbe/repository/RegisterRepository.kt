/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterRepository.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:47
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.repository

import team.applemango.runnerbe.domain.register.runnerbe.constant.UserRegisterResult
import team.applemango.runnerbe.domain.register.runnerbe.model.AccessToken
import team.applemango.runnerbe.domain.register.runnerbe.model.UserRegister
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken

interface RegisterRepository {
    /**
     * 로그인 요청 (SNS 로그인)
     *
     * @param platformName 플랫폼 타입 (카카오 (1번 API), 네이버 (2번 API), 애플)
     * @param accessToken 액세스 토큰 객체, API Call 할 때 Body 로 들어가서 data class 로 해야 함
     *
     * @return 회원가입이 다 된 유저라면 jwt 가 발급되고, 회원가입 정보가 없는 유저라면 uuid 가 발급 됨
     */
    suspend fun login(platformName: String, accessToken: AccessToken): UserToken

    /**
     * 가입 요청 (3번 API)
     *
     * @return 가입 요청 응답 enum class
     */
    suspend fun requestRegister(user: UserRegister): UserRegisterResult

    /**
     * 이메일 중복 확인 (4번 API)
     *
     * @return 이메일 사용 가능 여부 (비중복 여부), 사용 가능: true / 사용 불가능: false
     */
    suspend fun checkUsableEmail(email: String): Boolean
}
