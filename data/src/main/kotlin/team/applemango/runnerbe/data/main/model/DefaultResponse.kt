/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DefaultResponse.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.applemango.runnerbe.data.common.BaseResponse

data class DefaultResponse(
    @field:JsonProperty("code")
    override val code: Int? = null,

    @field:JsonProperty("message")
    override val message: String? = null,

    @field:JsonProperty("isSuccess")
    override val isSuccess: Boolean? = null,
) : BaseResponse
