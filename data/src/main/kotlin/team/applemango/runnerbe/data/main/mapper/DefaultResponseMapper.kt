/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemMapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:45
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.mapper

import team.applemango.runnerbe.data.main.model.DefaultResponse
import team.applemango.runnerbe.domain.main.common.BaseResult
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun DefaultResponse.toDomain(): BaseResult {
    return when (val code = requireNotNull(code) { requireFieldExceptionMessage("code") }) {
        1000 -> BaseResult.Success
        2044 -> BaseResult.NotYetVerify
        4000 -> BaseResult.DatabaseError
        else -> BaseResult.Exception(code)
    }
}