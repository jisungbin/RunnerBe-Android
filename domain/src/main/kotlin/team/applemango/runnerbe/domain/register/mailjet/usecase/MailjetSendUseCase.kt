/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailjetSendUseCase.kt] created by Ji Sungbin on 22. 2. 13. 오후 10:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.mailjet.usecase

import team.applemango.runnerbe.domain.register.mailjet.model.MailjetFrom
import team.applemango.runnerbe.domain.register.mailjet.model.MailjetTemplate
import team.applemango.runnerbe.domain.register.mailjet.model.MailjetTemplateItem
import team.applemango.runnerbe.domain.register.mailjet.model.MailjetTo
import team.applemango.runnerbe.domain.register.mailjet.repository.MailjetRepository

private fun String.toLink() = "https://jisungbin.github.io/verify?=$this"

private fun buildContent(token: String, isHtml: Boolean) =
    """
    안녕하세요. RunnerBe 입니다.
    만약 귀하께서 요청하신 인증 메일이 아니라면 이 메일을 무시하셔도 됩니다.

    하단의 링크를 클릭해 이메일 주소를 인증하면 회원님에 대한 모든 소개가 완료돼요.

    ${if (isHtml) "<a href=\"${token.toLink()}\">${token.toLink()} - html form</a>" else "${token.toLink()} - text form"}
                            
    그럼, 지금부터 러너비와 힘차게 달려볼까요?
    """.trimIndent().run {
        if (isHtml) replace("\n", "<br/>")
        else this
    }

class MailjetSendUseCase(private val repo: MailjetRepository) {
    suspend operator fun invoke(token: String, email: String) = runCatching {
        repo.send(
            MailjetTemplate(
                messages = listOf(
                    MailjetTemplateItem(
                        from = MailjetFrom(email = "welcome@runnerbe-auth.shop", name = "RunnerBe"),
                        to = listOf(MailjetTo(email = email)),
                        htmlPart = buildContent(token = token, isHtml = true),
                        textPart = buildContent(token = token, isHtml = false),
                        subject = "러너비에 오신걸 환영해요 \uD83E\uDD73", // 🥳
                    )
                )
            )
        )
    }
}
