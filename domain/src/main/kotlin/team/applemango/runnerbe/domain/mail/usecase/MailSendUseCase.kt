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

private fun buildLink(token: String) = "http://runnerbe-auth.shop/?verify=$token"

class MailSendUseCase(private val repo: MailRepository) {
    suspend operator fun invoke(token: String, email: String) = runCatching {
        repo.send(
            MailTemplate(
                messages = listOf(
                    MailTemplateItem(
                        from = From(email = "welcome@runnerbe-auth.shop", name = "RunnerBe"),
                        to = listOf(To(email = email)),
                        textPart = """
                            안녕하세요. RunnerBe 입니다.
                            만약 귀하께서 요청하신 인증 메일이 아니라면 이 메일을 무시하셔도 됩니다.

                            하단의 링크를 클릭해 이메일 주소를 인증하면 회원님에 대한 모든 소개가 완료돼요.

                            ${buildLink(token)}
                            
                            그럼, 지금부터 러너비와 힘차게 달려볼까요?
                        """.trimIndent(),
                        subject = "러너비에 오신걸 환영해요 \uD83E\uDD73", // 🥳
                    )
                )
            )
        )
    }
}
