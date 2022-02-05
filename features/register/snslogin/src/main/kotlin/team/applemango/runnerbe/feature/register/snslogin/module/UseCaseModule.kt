/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.applemango.runnerbe.domain.repository.LoginRepository
import team.applemango.runnerbe.domain.usecase.GetAccessTokenUseCase
import team.applemango.runnerbe.feature.register.snslogin.qualifier.Kakao
import team.applemango.runnerbe.feature.register.snslogin.qualifier.Naver

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {
    @Provides
    @Kakao
    @ViewModelScoped
    fun provideGetKakaoAccessTokenUseCase(@Kakao repo: LoginRepository): GetAccessTokenUseCase =
        GetAccessTokenUseCase(repo)

    @Provides
    @Naver
    @ViewModelScoped
    fun provideGetNaverAccessTokenUseCase(@Naver repo: LoginRepository): GetAccessTokenUseCase =
        GetAccessTokenUseCase(repo)
}
