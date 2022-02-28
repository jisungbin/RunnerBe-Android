/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [CheckDuplicateEmailResponseMapper.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.common

import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun DefaultResponse.toSuccessValueBoolean() = checkNotNull(isSuccess) {
    requireFieldExceptionMessage("isSuccess")
}
