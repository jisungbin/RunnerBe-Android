/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:42
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class LoginResult(
    @field:JsonProperty("jwt")
    val jwt: String? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("userId")
    val userId: Int? = null,

    @field:JsonProperty("uuid")
    val uuid: String? = null,
)
