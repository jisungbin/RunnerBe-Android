/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRegisterResponseMapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.mapper

import team.applemango.runnerbe.data.register.login.model.register.UserRegisterResponse
import team.applemango.runnerbe.domain.register.runnerbe.constant.UserRegisterResult
import team.applemango.runnerbe.shared.domain.util.notAllowedValueMessage
import team.applemango.runnerbe.shared.domain.util.requireFieldNullMessage

// 회원가입의 경우 처리가 어떻게 이뤄졌는지 사용자에게 나타내기 위해
// 사용자가 만들 수 있는 failure state 만 open 함
internal fun UserRegisterResponse.toDomain(): UserRegisterResult {
    checkNotNull(code) {
        requireFieldNullMessage("code")
    }
    return when (code) {
        1005, 1006 -> {
            checkNotNull(registerResult) {
                requireFieldNullMessage("registerResult")
            }
            UserRegisterResult.Success(
                userId = requireNotNull(registerResult.insertedUserId) {
                    requireFieldNullMessage("userId")
                },
                jwt = requireNotNull(registerResult.token) {
                    requireFieldNullMessage("token")
                }
            )
        }
        3001 -> UserRegisterResult.DuplicateUuid
        3002 -> UserRegisterResult.DuplicateEmail
        3004 -> UserRegisterResult.DuplicateNickname
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
}
