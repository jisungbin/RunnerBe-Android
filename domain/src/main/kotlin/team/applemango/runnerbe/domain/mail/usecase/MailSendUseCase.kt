/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailSendUseCase.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.mail.usecase

import team.applemango.runnerbe.domain.mail.model.From
import team.applemango.runnerbe.domain.mail.model.MailTemplate
import team.applemango.runnerbe.domain.mail.model.MailTemplateItem
import team.applemango.runnerbe.domain.mail.model.To
import team.applemango.runnerbe.domain.mail.repository.MailRepository

class MailSendUseCase(private val repo: MailRepository) {
    suspend operator fun invoke(token: String, email: String) = runCatching {
        repo.send(
            MailTemplate(
                mailTemplate = listOf(
                    MailTemplateItem(
                        from = From(email = "welcome@runnerbe-auth.shop", name = "RunnerBe"),
                        to = listOf(To(email = email)),
                        htmlPart = """
                            아래 링크를 통해 인증해 주세요 ><
                            <a href="http://runnerbe-auth.shop/verify=$token">http://runnerbe-auth.shop/verify=$token</a>
                        """.trimIndent(),
                        subject = "러너비에 오신걸 환영해요 \uD83E\uDD73", // 🥳
                    )
                )
            )
        )
    }
}
