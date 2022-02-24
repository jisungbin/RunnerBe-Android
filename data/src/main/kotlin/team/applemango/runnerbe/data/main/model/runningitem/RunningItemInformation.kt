/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 22. 2. 24. 오후 10:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.model.runningitem

import com.fasterxml.jackson.annotation.JsonProperty
import team.applemango.runnerbe.data.main.model.runner.RunnerData

data class RunningItemInformation(
    @field:JsonProperty("runnerInfo")
    val runners: List<RunnerData?>? = null,

    @field:JsonProperty("waitingRunnerInfo")
    val waitingRunners: List<RunnerData?>? = null,

    @field:JsonProperty("postingInfo")
    val item: List<RunningItemData?>? = null,
)
