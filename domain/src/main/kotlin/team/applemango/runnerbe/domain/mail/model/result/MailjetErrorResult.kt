/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailjetErrorResult.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.mail.model.result

data class MailjetErrorResult(
    val errorCode: String,
    val errorRelatedTo: String,
    val errorMessage: String,
    val statusCode: String,
)
