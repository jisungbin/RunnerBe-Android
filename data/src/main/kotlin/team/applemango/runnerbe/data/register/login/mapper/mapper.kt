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
import team.applemango.runnerbe.domain.login.model.UserToken
import team.applemango.runnerbe.domain.login.model.result.UserRegisterResult

private fun requireFieldExceptionMessage(fieldName: String) = "Require field $fieldName is null."

internal fun LoginRequestResponse.toDomain(): UserToken {
    return when (code!!) { // must 1001..1002, NonNull result
        1001 -> UserToken(jwt = requireNotNull(loginResult!!.jwt) { requireFieldExceptionMessage("jwt") }) // 회원
        else -> UserToken(uuid = requireNotNull(loginResult!!.uuid) { requireFieldExceptionMessage("uuid") }) // 비회원
    }
}

internal fun CheckDuplicateEmailResponse.toBoolean() =
    requireNotNull(isSuccess) { requireFieldExceptionMessage("isSuccess") }

internal fun UserRegisterResponse.toResultDomain(): UserRegisterResult {
    return when (val code = requireNotNull(code) { requireFieldExceptionMessage("code") }) {
        1005, 1006 -> UserRegisterResult.Success(
            requireNotNull(jwt) {
                requireFieldExceptionMessage("jwt")
            }
        )
        3001 -> UserRegisterResult.DuplicateUuid
        3002 -> UserRegisterResult.DuplicateEmail
        3004 -> UserRegisterResult.DuplicateNickname
        4000 -> UserRegisterResult.DatabaseError
        else -> UserRegisterResult.Exception(code)
    }
}
