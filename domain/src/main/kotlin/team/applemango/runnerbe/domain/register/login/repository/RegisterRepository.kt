/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterRepository.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:47
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.login.repository

import team.applemango.runnerbe.domain.register.login.model.UserRegister
import team.applemango.runnerbe.domain.register.login.constant.UserRegisterResult

interface RegisterRepository {
    /**
     * @return 이메일 사용 가능 여부 (비중복 여부), 사용 가능: true / 사용 불가능: false
     */
    suspend fun checkUsableEmail(email: String): Boolean

    /**
     * 가입 요청 쿼리
     * @return 가입 요청 응답 enum class
     */
    suspend fun register(user: UserRegister): UserRegisterResult
}
