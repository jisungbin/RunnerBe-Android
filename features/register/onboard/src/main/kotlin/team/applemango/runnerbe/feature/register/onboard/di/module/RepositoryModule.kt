/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.di.module

import team.applemango.runnerbe.data.firebase.repository.FirebaseRepositoryImpl
import team.applemango.runnerbe.data.register.login.repository.RegisterRepositoryImpl
import team.applemango.runnerbe.data.register.mailjet.repository.MailjetRepositoryImpl
import team.applemango.runnerbe.domain.firebase.repository.FirebaseRepository
import team.applemango.runnerbe.domain.register.mailjet.repository.MailjetRepository
import team.applemango.runnerbe.domain.register.runnerbe.repository.RegisterRepository

internal object RepositoryModule {
    val getRegisterRepository: RegisterRepository = RegisterRepositoryImpl()
    val getMailRepository: MailjetRepository = MailjetRepositoryImpl()
    val getFirebaseRepository: FirebaseRepository = FirebaseRepositoryImpl()
}
