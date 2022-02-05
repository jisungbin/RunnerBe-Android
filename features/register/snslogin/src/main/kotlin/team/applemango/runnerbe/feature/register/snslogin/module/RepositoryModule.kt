/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import team.applemango.runnerbe.data.login.repository.KakaoLoginRepositoryImpl
import team.applemango.runnerbe.data.login.repository.NaverLoginRepositoryImpl
import team.applemango.runnerbe.domain.repository.KakaoLoginRepository
import team.applemango.runnerbe.domain.repository.NaverLoginRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideKakaoLoginRepository(
        @ApplicationContext context: Context,
    ): KakaoLoginRepository = KakaoLoginRepositoryImpl(context)

    @Provides
    @ViewModelScoped
    fun provideNaverLoginRepository(
        @ApplicationContext context: Context,
    ): NaverLoginRepository = NaverLoginRepositoryImpl(context)
}
