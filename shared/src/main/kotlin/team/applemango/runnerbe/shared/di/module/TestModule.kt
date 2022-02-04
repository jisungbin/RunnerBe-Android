/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [TestProvideModule.kt] created by Ji Sungbin on 22. 2. 5. 오전 3:13
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import team.applemango.runnerbe.data.TestImpl
import team.applemango.runnerbe.domain.Test

@Module
@InstallIn(ActivityComponent::class)
abstract class TestModule {
    @Binds
    @ActivityScoped
    abstract fun providesTestRepo(impl: TestImpl): Test
}
