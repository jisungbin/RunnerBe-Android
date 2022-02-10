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
import team.applemango.runnerbe.domain.login.usecase.CheckEmailDuplicateUseCase
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.di.qualifier.ViewModelKey

@Module
internal class ViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(OnboardViewModel::class)
    fun provideSnsLoginViewModel(
        checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
    ): ViewModel {
        return OnboardViewModel(
            checkEmailDuplicateUseCase = checkEmailDuplicateUseCase
        )
    }
}
