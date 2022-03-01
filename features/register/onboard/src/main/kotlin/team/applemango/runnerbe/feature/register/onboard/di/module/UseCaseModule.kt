/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.di.module

import team.applemango.runnerbe.domain.firebase.usecase.ImageUploadUseCase
import team.applemango.runnerbe.domain.register.mailjet.usecase.MailjetSendUseCase
import team.applemango.runnerbe.domain.register.runnerbe.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.domain.register.runnerbe.usecase.UserRegisterUseCase

internal object UseCaseModule {
    fun provideCheckUsableEmailUseCase() =
        CheckUsableEmailUseCase(RepositoryModule.getRegisterRepository)

    fun provideUserRegisterUseCase() =
        UserRegisterUseCase(RepositoryModule.getRegisterRepository)

    fun provideMailSendUseCase() =
        MailjetSendUseCase(RepositoryModule.getMailRepository)

    fun provideImageUploadUseCase() =
        ImageUploadUseCase(RepositoryModule.getFirebaseRepository)
}
