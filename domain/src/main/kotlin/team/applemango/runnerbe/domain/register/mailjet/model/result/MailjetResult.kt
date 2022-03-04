/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailjetResult.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.mailjet.model.result

data class MailjetResult(
    val isSuccess: Boolean,
    val errorResult: MailjetErrorResult?,
)
