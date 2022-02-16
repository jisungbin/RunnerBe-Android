/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.mail.repository

import team.applemango.runnerbe.data.mail.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.mailjetApi
import team.applemango.runnerbe.domain.mail.model.MailTemplate
import team.applemango.runnerbe.domain.mail.model.result.MailjetResult
import team.applemango.runnerbe.domain.mail.repository.MailRepository

class MailRepositoryImpl : MailRepository {
    override suspend fun send(mailTemplate: MailTemplate): MailjetResult {
        val request = mailjetApi.send(mailTemplate)
        val isSuccess: Boolean
        return request.requireSuccessfulBody("Mail send").also { response ->
            isSuccess = response.messages?.first()?.status == "success"
        }.toDomain(isSuccess)
    }
}
