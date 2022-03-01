/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DefaultResponseNicknameChangeResultMapper.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.common

import team.applemango.runnerbe.data.runningitem.constant.NotYetVerifyCode
import team.applemango.runnerbe.data.runningitem.constant.SuccessCode
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.user.constant.JobChangeResult
import team.applemango.runnerbe.domain.user.constant.NicknameChangeResult
import team.applemango.runnerbe.shared.domain.notAllowedValueMessage
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage

internal val DefaultResponse.isSuccessNonNull
    get() = checkNotNull(isSuccess) {
        requireFieldNullMessage("isSuccess")
    }

internal fun DefaultResponse.toBaseResult() =
    when (checkNotNull(code) { requireFieldNullMessage("code") }) {
        SuccessCode -> BaseResult.Success
        NotYetVerifyCode -> BaseResult.NotYetVerify
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }

internal fun DefaultResponse.toNicknameChangeResult() =
    when (checkNotNull(code) { requireFieldNullMessage("code") }) {
        SuccessCode -> BaseResult.Success
        NotYetVerifyCode -> BaseResult.NotYetVerify
        3004 -> NicknameChangeResult.Duplicate
        3005 -> NicknameChangeResult.AlreadyChanged
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }

internal fun DefaultResponse.toJobChangeResult() =
    when (checkNotNull(code) { requireFieldNullMessage("code") }) {
        SuccessCode -> BaseResult.Success
        NotYetVerifyCode -> BaseResult.NotYetVerify
        2078 -> JobChangeResult.NotYetAvailable
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
