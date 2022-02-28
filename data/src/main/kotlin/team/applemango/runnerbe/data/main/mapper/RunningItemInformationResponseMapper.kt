/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemInformationResponseMapper.kt] created by Ji Sungbin on 22. 2. 25. 오전 5:30
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.mapper

import team.applemango.runnerbe.data.main.model.runningitem.information.RunningItemInformationResponse
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun RunningItemInformationResponse.toDomain(): RunningItemInformation {
    checkNotNull(result) { requireFieldExceptionMessage("result") }
    return RunningItemInformation(
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
