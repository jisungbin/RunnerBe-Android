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
import team.applemango.runnerbe.shared.domain.util.notAllowedValueMessage
import team.applemango.runnerbe.shared.domain.util.requireFieldNullMessage

// 1001, 1007 - JWT (회원)
// 1002 - UUID (비회원)
internal fun LoginRequestResponse.toDomain(): UserToken {
    checkNotNull(loginResult) {
        requireFieldNullMessage("loginResult")
    }
    checkNotNull(code) {
        requireFieldNullMessage("code")
    }
    return when (code) {
        1001, 1007 -> UserToken( // 회원
            jwt = requireNotNull(loginResult.jwt) {
                requireFieldNullMessage("jwt")
            },
            userId = requireNotNull(loginResult.userId) {
                requireFieldNullMessage("userId")
            }
        )
        1002 -> UserToken( // 비회원
            uuid = requireNotNull(loginResult.uuid) {
                requireFieldNullMessage("uuid")
            }
        )
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
}
