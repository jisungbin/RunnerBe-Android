/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailjetApi.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:58
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.mail.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import team.applemango.runnerbe.data.mail.model.MailjetResponse
import team.applemango.runnerbe.domain.mail.MailTemplate

interface MailjetService {
    @POST("/v3.1/send")
    suspend fun send(@Body mailTemplate: MailTemplate): Response<MailjetResponse>
}
