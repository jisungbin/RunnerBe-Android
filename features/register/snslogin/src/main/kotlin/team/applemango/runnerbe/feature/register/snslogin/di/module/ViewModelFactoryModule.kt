/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ViewModelFactoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오후 2:46
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import team.applemango.runnerbe.feature.register.snslogin.di.ViewModelFactory

@Module
internal abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
