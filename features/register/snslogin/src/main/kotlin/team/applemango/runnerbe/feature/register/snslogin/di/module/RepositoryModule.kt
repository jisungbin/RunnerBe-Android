/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.di.module

import android.app.Activity
import team.applemango.runnerbe.data.register.login.repository.AccessTokenRepositoryImpl
import team.applemango.runnerbe.data.register.login.repository.RegisterRepositoryImpl
import team.applemango.runnerbe.domain.register.runnerbe.repository.AccessTokenRepository
import team.applemango.runnerbe.domain.register.runnerbe.repository.RegisterRepository

internal class RepositoryModule(private val activityContext: Activity) {
    fun provideAccessTokenRepository(): AccessTokenRepository =
        AccessTokenRepositoryImpl(activityContext)

    val getLoginRepository: RegisterRepository = RegisterRepositoryImpl()
}
