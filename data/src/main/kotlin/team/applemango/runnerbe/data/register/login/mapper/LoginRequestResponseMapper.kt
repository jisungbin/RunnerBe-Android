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
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage
import team.applemango.runnerbe.shared.domain.notAllowedValueMessage

internal fun LoginRequestResponse.toDomain(): UserToken {
    val loginResult = checkNotNull(loginResult) { requireFieldNullMessage("loginResult") }
    return when (checkNotNull(code) { requireFieldNullMessage("code") }) {
        1001 -> UserToken(jwt = requireNotNull(loginResult.jwt) { requireFieldNullMessage("jwt") }) // 회원
        1007 -> UserToken(uuid = requireNotNull(loginResult.uuid) { requireFieldNullMessage("uuid") }) // 비회원
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
}
