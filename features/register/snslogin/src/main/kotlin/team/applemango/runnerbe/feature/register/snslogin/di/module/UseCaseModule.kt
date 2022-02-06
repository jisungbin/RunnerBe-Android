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
import team.applemango.runnerbe.domain.usecase.GetAccessTokenUseCase
import team.applemango.runnerbe.feature.register.snslogin.di.qualifier.Kakao
import team.applemango.runnerbe.feature.register.snslogin.di.qualifier.Naver

@Module
internal class UseCaseModule {
    @Provides
    @Kakao
    fun provideGetKakaoAccessTokenUseCase(@Kakao repo: AccessTokenRepository): GetAccessTokenUseCase =
        GetAccessTokenUseCase(repo)

    @Provides
    @Naver
    fun provideGetNaverAccessTokenUseCase(@Naver repo: AccessTokenRepository): GetAccessTokenUseCase =
        GetAccessTokenUseCase(repo)
}
