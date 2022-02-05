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
import team.applemango.runnerbe.domain.repository.LoginRepository
import team.applemango.runnerbe.feature.register.snslogin.qualifier.Kakao
import team.applemango.runnerbe.feature.register.snslogin.qualifier.Naver

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {
    @Provides
    @Kakao
    @ViewModelScoped
    fun provideKakaoLoginRepository(
        @ApplicationContext context: Context,
    ): LoginRepository = KakaoLoginRepositoryImpl(context)

    @Provides
    @Naver
    @ViewModelScoped
    fun provideNaverLoginRepository(
        @ApplicationContext context: Context,
    ): LoginRepository = NaverLoginRepositoryImpl(context)
}
