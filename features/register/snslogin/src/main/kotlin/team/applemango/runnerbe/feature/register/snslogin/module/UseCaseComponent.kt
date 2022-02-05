/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryComponent.java] created by Ji Sungbin on 22. 2. 6. 오전 3:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */
package team.applemango.runnerbe.feature.register.snslogin.module

import dagger.Component
import team.applemango.runnerbe.feature.register.snslogin.SnsLoginActivity

@Component(modules = [UseCaseModule::class])
internal interface UseCaseComponent {
    fun inject(activity: SnsLoginActivity)
}
