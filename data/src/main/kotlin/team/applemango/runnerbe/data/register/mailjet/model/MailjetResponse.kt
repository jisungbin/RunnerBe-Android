/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailResponse.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.mailjet.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MailjetResponse(
    @field:JsonProperty("Messages")
    val messages: List<MailjetItem?>? = null,
)
