/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [mapper.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.mapper

import team.applemango.runnerbe.data.login.model.CheckDuplicateEmailResponse
import team.applemango.runnerbe.data.login.model.LoginRequestResponse
import team.applemango.runnerbe.data.login.model.UserRegisterResponse
import team.applemango.runnerbe.domain.login.model.UserToken
import team.applemango.runnerbe.domain.login.model.result.UserRegisterResult

internal fun LoginRequestResponse.toDomain(): UserToken {
    return when (code!!) { // must 1001..1002, NonNull result
        1001 -> UserToken(jwt = requireField(loginResult!!.jwt, "jwt")) // 회원
        else -> UserToken(uuid = requireField(loginResult!!.uuid, "uuid")) // 비회원
    }
}

internal fun CheckDuplicateEmailResponse.toBoolean() = requireField(isSuccess, "isSuccess")

internal fun UserRegisterResponse.toResultDomain(): UserRegisterResult {
    return when (requireField(code, "code")) {
        1000 -> UserRegisterResult.Success
        3001 -> UserRegisterResult.DuplicateUuid
        3002 -> UserRegisterResult.DuplicateEmail
        3004 -> UserRegisterResult.DuplicateNickname
        4000 -> UserRegisterResult.DatabaseError
        else -> UserRegisterResult.Exception
    }
}

private fun <T> requireField(value: T?, fieldName: String): T {
    if (value == null) throw NullPointerException("Require `$fieldName` field is null.")
    return value
}
