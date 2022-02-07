package team.applemango.runnerbe.shared.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import team.applemango.runnerbe.shared.di.qualifier.IoDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelDispatcherModule {
    @Provides
    @IoDispatcher
    @ViewModelScoped
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
