/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRequestResponseMapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.mapper

import team.applemango.runnerbe.data.register.login.model.login.LoginRequestResponse
import team.applemango.runnerbe.domain.register.login.model.UserToken
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun LoginRequestResponse.toDomain(): UserToken {
    val code = checkNotNull(code) { requireFieldExceptionMessage("code") }
    val loginResult = checkNotNull(loginResult) { requireFieldExceptionMessage("loginResult") }
    check(code in listOf(1001, 1007)) { "Success result code must be 1001 or 1007." }
    return when (code) { // must 1001, 1007
        1001 -> UserToken(jwt = requireNotNull(loginResult.jwt) { requireFieldExceptionMessage("jwt") }) // 회원
        else -> UserToken(uuid = requireNotNull(loginResult.uuid) { requireFieldExceptionMessage("uuid") }) // 비회원
    }
}