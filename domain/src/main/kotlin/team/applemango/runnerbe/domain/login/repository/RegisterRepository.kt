/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterRepository.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:47
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.login.repository

import team.applemango.runnerbe.domain.login.model.User

interface RegisterRepository {
    /**
     * @return 이메일 중복 여부 (boolean)
     */
    suspend fun checkDuplicateEmail(email: String): Boolean

    /**
     * 가입 요청 쿼리
     */
    suspend fun register(user: User) // TODO
}
