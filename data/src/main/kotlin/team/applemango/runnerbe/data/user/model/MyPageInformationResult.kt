/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MyPageInformationResult.kt] created by Ji Sungbin on 22. 3. 1. 오후 3:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.applemango.runnerbe.data.runningitem.model.runner.RunnerData
import team.applemango.runnerbe.data.runningitem.model.runningitem.RunningItemData

data class MyPageInformationResult(
    @field:JsonProperty("myInfo")
    val myInfo: List<RunnerData?>? = null,

    @field:JsonProperty("myPosting")
    val myPosting: List<RunningItemData?>? = null,

    @field:JsonProperty("myRunning")
    val myRunning: List<RunningItemData?>? = null,
)
