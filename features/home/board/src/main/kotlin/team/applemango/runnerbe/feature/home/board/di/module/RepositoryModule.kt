/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 3. 2. 오후 9:28
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.applemango.runnerbe.data.runningitem.repository.RunningItemRepositoryImpl
import team.applemango.runnerbe.data.user.repository.UserRepositoryImpl
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository
import team.applemango.runnerbe.domain.user.repository.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    @ViewModelScoped
    fun provideRunningItemRepository(): RunningItemRepository = RunningItemRepositoryImpl()
}