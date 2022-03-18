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
import team.applemango.runnerbe.shared.domain.util.notAllowedValueMessage
import team.applemango.runnerbe.shared.domain.util.requireFieldNullMessage

internal val DefaultResponse.isSuccessNonNull
    get() = requireNotNull(isSuccess) {
        requireFieldNullMessage("isSuccess")
    }

internal fun DefaultResponse.toBaseResult(): BaseResult {
    checkNotNull(code) {
        requireFieldNullMessage("code")
    }
    return when (code) {
        SuccessCode -> BaseResult.Success
        NotYetVerifyCode -> BaseResult.NotYetVerify
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
}

internal fun DefaultResponse.toNicknameChangeResult(): BaseResult {
    checkNotNull(code) {
        requireFieldNullMessage("code")
    }
    return when (code) {
        SuccessCode -> BaseResult.Success
        NotYetVerifyCode -> BaseResult.NotYetVerify
        3004 -> NicknameChangeResult.Duplicate
        3005 -> NicknameChangeResult.AlreadyChanged
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
}

internal fun DefaultResponse.toJobChangeResult(): BaseResult {
    checkNotNull(code) {
        requireFieldNullMessage("code")
    }
    return when (code) {
        SuccessCode -> BaseResult.Success
        NotYetVerifyCode -> BaseResult.NotYetVerify
        2078 -> JobChangeResult.NotYetAvailable
        else -> throw IllegalStateException(notAllowedValueMessage(code))
    }
}
