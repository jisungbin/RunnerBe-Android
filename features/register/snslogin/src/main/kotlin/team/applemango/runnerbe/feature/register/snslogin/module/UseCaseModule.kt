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
import team.applemango.runnerbe.domain.repository.KakaoLoginRepository
import team.applemango.runnerbe.domain.repository.NaverLoginRepository
import team.applemango.runnerbe.domain.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.usecase.GetNaverAccessTokenUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetKakaoAccessTokenUseCase(repo: KakaoLoginRepository): GetKakaoAccessTokenUseCase =
        GetKakaoAccessTokenUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideGetNaverAccessTokenUseCase(repo: NaverLoginRepository): GetNaverAccessTokenUseCase =
        GetNaverAccessTokenUseCase(repo)
}
