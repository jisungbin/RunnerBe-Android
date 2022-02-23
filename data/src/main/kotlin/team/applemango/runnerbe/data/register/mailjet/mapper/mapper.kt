/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [mapper.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.mailjet.mapper

import team.applemango.runnerbe.data.register.mailjet.model.MailjetResponse
import team.applemango.runnerbe.domain.mail.model.result.MailjetErrorResult
import team.applemango.runnerbe.domain.mail.model.result.MailjetResult

internal fun MailjetResponse.toDomain(isSuccess: Boolean) = when (isSuccess) {
    true -> MailjetResult(isSuccess = true, errorResult = null)
    else -> MailjetResult(
        isSuccess = false,
        errorResult = with(messages?.first()?.errors?.first()) {
            MailjetErrorResult(
                errorCode = this?.errorCode.toString(),
                errorRelatedTo = this?.errorRelatedTo?.first().toString(),
                errorMessage = this?.errorMessage.toString(),
                statusCode = this?.statusCode.toString()
            )
        }
    )
}
