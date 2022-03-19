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
import team.applemango.runnerbe.shared.domain.util.notAllowedValueMessage
import team.applemango.runnerbe.shared.domain.util.requireFieldNullMessage

internal fun RunningItemInformationResponse.toDomain(): RunningItemInformation? {
    checkNotNull(result) {
        requireFieldNullMessage("result")
    }
    checkNotNull(code) {
        requireFieldNullMessage("code")
    }
    check(code in 1015..1020 || code == NotYetVerifyCode) {
        notAllowedValueMessage(code)
    }
    if (code == NotYetVerifyCode) return null
    return RunningItemInformation(
        isMyItem = when (code) {
            in 1015..1018 -> false
            in 1019..1020 -> true
            else -> throw IllegalStateException(notAllowedValueMessage(code))
        },
        bookmarked = when (code) {
            1015, 1017, 1019 -> true
            1016, 1018, 1020 -> false
            else -> throw IllegalStateException(notAllowedValueMessage(code))
        },
        item = requireNotNull(result.postingInfo) {
            requireFieldNullMessage("postingInfo")
        }.firstOrNull()
            ?.toDomain(type = MappingType.InformationApiFields)
            ?: throw Exception(requireFieldNullMessage("postingInfo value")),
        joinRunners = requireNotNull(result.runnerInfo) {
            requireFieldNullMessage("runnerInfo")
        }.map { runner ->
            checkNotNull(runner) {
                requireFieldNullMessage("runner")
            }
            runner.toDomain()
        },
        waitingRunners = requireNotNull(result.waitingRunnerInfo) {
            requireFieldNullMessage("waitingRunnerInfo")
        }.map { runner ->
            checkNotNull(runner) {
                requireFieldNullMessage("runner")
            }
            runner.toDomain()
        },
    )
}
