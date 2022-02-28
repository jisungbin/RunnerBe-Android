/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemInformationResponseMapper.kt] created by Ji Sungbin on 22. 2. 25. 오전 5:30
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.mapper

import team.applemango.runnerbe.data.runningitem.constant.NotYetVerifyCode
import team.applemango.runnerbe.data.runningitem.model.runningitem.information.RunningItemInformationResponse
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage
import team.applemango.runnerbe.shared.domain.resultCodeExceptionMessage

internal fun RunningItemInformationResponse.toDomain(): RunningItemInformation? {
    checkNotNull(result) { requireFieldExceptionMessage("result") }
    checkNotNull(code) { requireFieldExceptionMessage("code") }
    check(code in 1015..1020 || code == NotYetVerifyCode) { resultCodeExceptionMessage(code) }
    if (code == NotYetVerifyCode) return null
    return RunningItemInformation(
        isMyItem = when (code) {
            in 1015..1018 -> false
            in 1019..1020 -> true
            else -> throw IllegalStateException(resultCodeExceptionMessage(code))
        },
        bookmarked = when (code) {
            1015, 1017, 1019 -> true
            1016, 1018, 1020 -> false
            else -> throw IllegalStateException(resultCodeExceptionMessage(code))
        },
        item = requireNotNull(result.postingInfo) {
            requireFieldExceptionMessage("postingInfo")
        }.firstOrNull()
            ?.toDomain(type = MappingType.InformationApiFields)
            ?: throw Exception(requireFieldExceptionMessage("postingInfo value")),
        joinRunners = requireNotNull(result.runnerInfo) {
            requireFieldExceptionMessage("runnerInfo")
        }.map { runner ->
            checkNotNull(runner) { requireFieldExceptionMessage("runner") }
            runner.toDomain()
        },
        waitingRunners = requireNotNull(result.waitingRunnerInfo) {
            requireFieldExceptionMessage("waitingRunnerInfo")
        }.map { runner ->
            checkNotNull(runner) { requireFieldExceptionMessage("runner") }
            runner.toDomain()
        },
    )
}
