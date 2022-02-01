/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [TestBindModule.kt] created by Ji Sungbin on 22. 2. 1. 오전 11:02
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import team.applemango.runnerbe.shared.di.test.TestRepo
import team.applemango.runnerbe.shared.di.test.TestRepoImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class TestBindModule {
    @Binds
    @ActivityScoped
    abstract fun bindTestRepo(impl: TestRepoImpl): TestRepo
}
