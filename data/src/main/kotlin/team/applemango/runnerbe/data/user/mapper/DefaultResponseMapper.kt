/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DefaultResponseMapper.kt] created by Ji Sungbin on 22. 2. 28. 오후 11:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.mapper

import team.applemango.runnerbe.data.common.DefaultResponse
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.user.constant.NicknameChangeResult
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage
import team.applemango.runnerbe.shared.domain.resultCodeExceptionMessage

fun DefaultResponse.toNicknameChangeResult(): BaseResult {
    return when (checkNotNull(code) { requireFieldExceptionMessage("code") }) {
        1000 -> BaseResult.Success
        2044 -> BaseResult.NotYetVerify
        3004 -> NicknameChangeResult.Duplicate
        3005 -> NicknameChangeResult.AlreadyChanged
        else -> throw IllegalStateException(resultCodeExceptionMessage(code))
    }
}