/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 3. 2. 오후 9:28
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import team.applemango.runnerbe.data.runningitem.repository.RunningItemRepositoryImpl
import team.applemango.runnerbe.data.user.repository.UserRepositoryImpl
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository
import team.applemango.runnerbe.domain.user.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    @Singleton
    fun provideRunningItemRepository(): RunningItemRepository = RunningItemRepositoryImpl()
}
