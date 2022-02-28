/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 22. 2. 24. 오후 10:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.model.runningitem.information

import com.fasterxml.jackson.annotation.JsonProperty
import team.applemango.runnerbe.data.runningitem.model.runner.RunnerData
import team.applemango.runnerbe.data.runningitem.model.runningitem.RunningItemData

data class RunningItemInformationData(
    @field:JsonProperty("postingInfo")
    val postingInfo: List<RunningItemData?>? = null,

    @field:JsonProperty("runnerInfo")
    val runnerInfo: List<RunnerData?>? = null,

    @field:JsonProperty("waitingRunnerInfo")
    val waitingRunnerInfo: List<RunnerData?>? = null,
)
