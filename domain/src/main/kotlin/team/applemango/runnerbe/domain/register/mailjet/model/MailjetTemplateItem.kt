/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailTemplateItem.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.mailjet.model

/**
 * When the HTML part is the only part provided,
 * Mailjet will not generate a Text-part from the HTML version.
 */
data class MailjetTemplateItem(
    val htmlPart: String,
    val textPart: String,
    val from: MailjetFrom,
    val to: List<MailjetTo>,
    val subject: String,
)
