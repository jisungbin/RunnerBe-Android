/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NaverLoginRepository.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:08
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.repository

/**
 * 소셜 로그인 액세스 토큰 발급 API -> 각 플랫폼별 로그인 SDK 사용
 */
interface AccessTokenRepository {
    suspend fun getKakao(): String
    suspend fun getNaver(): String
}
