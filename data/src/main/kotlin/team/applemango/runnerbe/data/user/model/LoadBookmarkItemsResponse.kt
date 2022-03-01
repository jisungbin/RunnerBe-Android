/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoadBookmarkItemsResponse.kt] created by Ji Sungbin on 22. 3. 1. 오전 9:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoadBookmarkItemsResponse(
    @field:JsonProperty("result")
    val result: LoadBookmarkItemsResult? = null,

    @field:JsonProperty("code")
    val code: Int? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("isSuccess")
    val isSuccess: Boolean? = null,
)