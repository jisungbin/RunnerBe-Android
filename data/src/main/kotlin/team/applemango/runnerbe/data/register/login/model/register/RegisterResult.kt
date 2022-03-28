/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 22. 3. 29. 오전 1:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.model.register

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterResult(
    @field:JsonProperty("insertedUserId")
    val insertedUserId: Int? = null,

    @field:JsonProperty("token")
    val token: String? = null,
)
