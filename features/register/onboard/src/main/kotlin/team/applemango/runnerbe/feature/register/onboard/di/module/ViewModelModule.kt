/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ViewModelModule.kt] created by Ji Sungbin on 22. 2. 6. 오후 2:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.di.module

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import team.applemango.runnerbe.domain.login.usecase.CheckUsableEmailUseCase
import team.applemango.runnerbe.domain.login.usecase.UserRegisterUseCase
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.di.qualifier.ViewModelKey

@Module
internal class ViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(OnboardViewModel::class)
    fun provideOnboardViewModel(
        checkUsableEmailUseCase: CheckUsableEmailUseCase,
        userRegisterUseCase: UserRegisterUseCase,
    ): ViewModel {
        return OnboardViewModel(
            checkUsableEmailUseCase = checkUsableEmailUseCase,
            userRegisterUseCase = userRegisterUseCase
        )
    }
}
