/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [mapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.mapper.register

import team.applemango.runnerbe.data.register.login.model.register.UserRegisterResponse
import team.applemango.runnerbe.domain.register.login.constant.UserRegisterResult
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun UserRegisterResponse.toResultDomain(): UserRegisterResult {
    return when (val code = checkNotNull(code) { requireFieldExceptionMessage("code") }) {
        1005, 1006 -> {
            val jwt = requireNotNull(jwt) { requireFieldExceptionMessage("jwt") }
            UserRegisterResult.Success(jwt)
        }
        3001 -> UserRegisterResult.DuplicateUuid
        3002 -> UserRegisterResult.DuplicateEmail
        3004 -> UserRegisterResult.DuplicateNickname
        4000 -> UserRegisterResult.DatabaseError
        else -> UserRegisterResult.Exception(code)
    }
}
