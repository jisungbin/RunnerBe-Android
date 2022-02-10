/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.di.module

import dagger.Module
import dagger.Provides
import team.applemango.runnerbe.data.login.repository.RegisterRepositoryImpl
import team.applemango.runnerbe.domain.login.repository.RegisterRepository

@Module
internal class RepositoryModule {
    @Provides
    fun provideRegisterRepository(): RegisterRepository = RegisterRepositoryImpl()
}
