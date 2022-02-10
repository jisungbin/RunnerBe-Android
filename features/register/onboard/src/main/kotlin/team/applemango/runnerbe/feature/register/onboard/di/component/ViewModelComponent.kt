/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryComponent.java] created by Ji Sungbin on 22. 2. 6. 오전 3:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.di.component

import dagger.Component
import team.applemango.runnerbe.feature.register.onboard.OnboardActivity
import team.applemango.runnerbe.feature.register.onboard.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.onboard.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.onboard.di.module.ViewModelFactoryModule
import team.applemango.runnerbe.feature.register.onboard.di.module.ViewModelModule

@Component(
    modules = [
        UseCaseModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class
    ]
)
internal interface ViewModelComponent {
    fun inject(activity: OnboardActivity)
}
