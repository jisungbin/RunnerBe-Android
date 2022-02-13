/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MessagesItem.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.mail.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MessagesItem(
    @field:JsonProperty("Status")
    val status: String? = null,

    @field:JsonProperty("Errors")
    val errors: List<ErrorsItem?>? = null,

    @field:JsonProperty("To")
    val to: List<ToItem?>? = null,
)
