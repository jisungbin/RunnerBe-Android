/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ToItem.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.mail.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ToItem(
    @field:JsonProperty("Email")
    val email: String? = null,

    @field:JsonProperty("MessageHref")
    val messageHref: String? = null,

    @field:JsonProperty("MessageUUID")
    val messageUUID: String? = null,

    @field:JsonProperty("MessageID")
    val messageID: Int? = null,
)
