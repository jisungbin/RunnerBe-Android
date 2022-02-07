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
import dagger.Module
import dagger.Provides
import team.applemango.runnerbe.data.login.repository.AccessTokenRepositoryImpl
import team.applemango.runnerbe.data.login.repository.LoginRepositoryImpl
import team.applemango.runnerbe.domain.login.repository.AccessTokenRepository
import team.applemango.runnerbe.domain.login.repository.LoginRepository

@Module
internal class RepositoryModule(private val activityContext: Activity) {
    @Provides
    fun provideAccessTokenRepository(): AccessTokenRepository =
        AccessTokenRepositoryImpl(activityContext)

    @Provides
    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()
}
