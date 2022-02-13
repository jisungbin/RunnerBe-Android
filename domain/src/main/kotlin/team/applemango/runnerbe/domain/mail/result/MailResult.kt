/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailResult.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.mail.result

data class MailResult(val isSuccess: Boolean, val errorResult: MailErrorResult)
