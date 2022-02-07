/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.di.module

import dagger.Module
import dagger.Provides
import team.applemango.runnerbe.domain.repository.AccessTokenRepository
import team.applemango.runnerbe.domain.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.usecase.GetNaverAccessTokenUseCase

@Module
internal class UseCaseModule {
    @Provides
    fun provideGetKakaoAccessTokenUseCase(repo: AccessTokenRepository): GetKakaoAccessTokenUseCase =
        GetKakaoAccessTokenUseCase(repo)

    @Provides
    fun provideGetNaverAccessTokenUseCase(repo: AccessTokenRepository): GetNaverAccessTokenUseCase =
        GetNaverAccessTokenUseCase(repo)
}
