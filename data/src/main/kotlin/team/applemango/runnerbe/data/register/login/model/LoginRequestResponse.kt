/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRequestResponse.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:42
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class LoginRequestResponse(
    @field:JsonProperty("result")
    val loginResult: LoginResult? = null,

    @field:JsonProperty("code")
    val code: Int? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("isSuccess")
    val isSuccess: Boolean? = null,
)
