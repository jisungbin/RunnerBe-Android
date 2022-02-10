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
import team.applemango.runnerbe.domain.login.model.User

internal fun LoginRequestResponse.toDomain(): User {
    return when (code!!) { // must 1001..1002, NonNull result
        1001 -> User(jwt = requireField(result!!.jwt, "jwt")) // 회원
        else -> User(uuid = requireField(result!!.uuid, "uuid")) // 비회원
    }
}

internal fun CheckDuplicateEmailResponse.toBoolean() = requireField(isSuccess, "isSuccess")

private fun <T> requireField(value: T?, fieldName: String): T {
    if (value == null) throw NullPointerException("Require `$fieldName` field is null.")
    return value
}
