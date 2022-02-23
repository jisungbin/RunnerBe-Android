/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.di.module

import dagger.Module
import dagger.Provides
import team.applemango.runnerbe.domain.register.login.repository.RegisterRepository
import team.applemango.runnerbe.domain.register.login.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.domain.register.login.usecase.UserRegisterUseCase
import team.applemango.runnerbe.domain.register.mailjet.repository.MailjetRepository
import team.applemango.runnerbe.domain.register.mailjet.usecase.MailjetSendUseCase

@Module
internal class UseCaseModule {
    @Provides
    fun provideCheckUsableEmailUseCase(repo: RegisterRepository): CheckUsableEmailUseCase =
        CheckUsableEmailUseCase(repo)

    @Provides
    fun provideUserRegisterUseCase(repo: RegisterRepository): UserRegisterUseCase =
        UserRegisterUseCase(repo)

    @Provides
    fun provideMailSendUseCase(repo: MailjetRepository): MailjetSendUseCase = MailjetSendUseCase(repo)
}
