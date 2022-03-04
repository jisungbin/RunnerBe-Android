/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MyInformationResponse.kt] created by Ji Sungbin on 22. 3. 1. 오후 2:58
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.applemango.runnerbe.data.common.BaseResponse

data class MyPageInformationResponse(
    @field:JsonProperty("result")
    val result: MyPageInformationResult? = null,

    @field:JsonProperty("code")
    override val code: Int? = null,

    @field:JsonProperty("message")
    override val message: String? = null,

    @field:JsonProperty("isSuccess")
    override val isSuccess: Boolean? = null,
) : BaseResponse
