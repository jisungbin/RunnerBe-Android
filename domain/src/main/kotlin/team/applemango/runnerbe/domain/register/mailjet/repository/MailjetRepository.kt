/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailRepository.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.mailjet.repository

import team.applemango.runnerbe.domain.register.mailjet.model.MailjetTemplate
import team.applemango.runnerbe.domain.register.mailjet.model.result.MailjetResult

interface MailjetRepository {
    suspend fun send(mailjetTemplate: MailjetTemplate): MailjetResult
}
