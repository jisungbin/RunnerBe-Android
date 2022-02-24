/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemInformationResponse.kt] created by Ji Sungbin on 22. 2. 24. 오후 10:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.model.information

import com.fasterxml.jackson.annotation.JsonProperty
import team.applemango.runnerbe.data.common.BaseResponse

data class RunningItemInformationResponse(
    @field:JsonProperty("result")
    val result: RunningItemInformation? = null,

    @field:JsonProperty("code")
    override val code: Int? = null,

    @field:JsonProperty("message")
    override val message: String? = null,

    @field:JsonProperty("isSuccess")
    override val isSuccess: Boolean? = null,
) : BaseResponse
