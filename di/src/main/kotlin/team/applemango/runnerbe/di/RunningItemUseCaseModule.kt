/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemUseCaseModule.kt] created by Ji Sungbin on 22. 3. 20. 오전 12:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemsUseCase
import team.applemango.runnerbe.domain.runningitem.usecase.WriteRunningItemUseCase

@Module
@InstallIn(ViewModelComponent::class)
object RunningItemUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideLoadRunningItemsUseCase(repo: RunningItemRepository): LoadRunningItemsUseCase =
        LoadRunningItemsUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideWriteRunningItemUseCase(repo: RunningItemRepository): WriteRunningItemUseCase =
        WriteRunningItemUseCase(repo)
}
