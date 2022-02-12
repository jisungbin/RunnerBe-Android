/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRegisterResponse.kt] created by Ji Sungbin on 22. 2. 12. 오후 2:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegisterResponse(
    @field:JsonProperty("code")
    val code: Int? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("isSuccess")
    val isSuccess: Boolean? = null,

    @field:JsonProperty("result")
    val jwt: String? = null,
)
