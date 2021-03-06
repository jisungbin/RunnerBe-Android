/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailTemplate.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.mailjet.model

data class MailjetTemplate(
    val messages: List<MailjetTemplateItem>,
)
