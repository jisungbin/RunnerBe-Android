/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [mapper.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.mapper

import team.applemango.runnerbe.data.register.login.model.CheckDuplicateEmailResponse
import team.applemango.runnerbe.data.register.login.model.LoginRequestResponse
import team.applemango.runnerbe.data.register.login.model.UserRegisterResponse
import team.applemango.runnerbe.data.util.requireFieldExceptionMessage
import team.applemango.runnerbe.domain.register.login.model.UserToken
import team.applemango.runnerbe.domain.register.login.model.result.UserRegisterResult

internal fun LoginRequestResponse.toDomain(): UserToken {
    val code = checkNotNull(code) { requireFieldExceptionMessage("code") }
    val loginResult = checkNotNull(loginResult) { requireFieldExceptionMessage("loginResult") }
    check(code in listOf(1001, 1007)) { "Success result code must be 1001 or 1007." }
    return when (code) { // must 1001, 1007
        1001 -> UserToken(jwt = requireNotNull(loginResult.jwt) { requireFieldExceptionMessage("jwt") }) // 회원
        else -> UserToken(uuid = requireNotNull(loginResult.uuid) { requireFieldExceptionMessage("uuid") }) // 비회원
    }
}

internal fun CheckDuplicateEmailResponse.toBoolean() =
    checkNotNull(isSuccess) { requireFieldExceptionMessage("isSuccess") }

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
