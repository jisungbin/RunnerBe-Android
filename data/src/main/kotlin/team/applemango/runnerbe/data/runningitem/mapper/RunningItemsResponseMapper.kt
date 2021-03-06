/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemsResponseMapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 3:03
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.mapper

import team.applemango.runnerbe.data.runningitem.model.runningitem.RunningItemsResponse
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.shared.domain.util.requireFieldNullMessage

internal fun RunningItemsResponse.toDomain(): List<RunningItem> {
    if (result.isNullOrEmpty()) return emptyList()
    return result.map { item ->
        checkNotNull(item) {
            requireFieldNullMessage("RunningItemResponse.result item")
        }
        item.toDomain()
    }
}
