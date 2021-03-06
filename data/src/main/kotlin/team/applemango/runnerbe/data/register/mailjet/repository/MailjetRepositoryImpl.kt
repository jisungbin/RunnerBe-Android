/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.mailjet.repository

import team.applemango.runnerbe.data.register.mailjet.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.mailjetApi
import team.applemango.runnerbe.domain.register.mailjet.model.MailjetTemplate
import team.applemango.runnerbe.domain.register.mailjet.model.result.MailjetResult
import team.applemango.runnerbe.domain.register.mailjet.repository.MailjetRepository

class MailjetRepositoryImpl : MailjetRepository {
    override suspend fun send(mailjetTemplate: MailjetTemplate): MailjetResult {
        val request = mailjetApi.send(mailjetTemplate)
        val isSuccess: Boolean
        return request.requireSuccessfulBody(
            requestName = "mailjetApi.send",
            resultVerifyBuilder = { true }
        ).also { response ->
            isSuccess = response.messages?.first()?.status == "success"
        }.toDomain(isSuccess)
    }
}
