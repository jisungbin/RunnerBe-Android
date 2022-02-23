/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemResponse.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RunningItemResponse(
    @field:JsonProperty("result")
    val result: List<RunningItem?>? = null,

    @field:JsonProperty("code")
    val code: Int? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("isSuccess")
    val isSuccess: Boolean? = null,
)
