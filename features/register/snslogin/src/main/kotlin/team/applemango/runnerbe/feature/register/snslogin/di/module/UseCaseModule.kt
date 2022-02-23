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
import team.applemango.runnerbe.domain.register.login.repository.AccessTokenRepository
import team.applemango.runnerbe.domain.register.login.repository.LoginRepository
import team.applemango.runnerbe.domain.register.login.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.register.login.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.domain.register.login.usecase.LoginUseCase

@Module
internal class UseCaseModule {
    @Provides
    fun provideGetKakaoAccessTokenUseCase(repo: AccessTokenRepository): GetKakaoAccessTokenUseCase =
        GetKakaoAccessTokenUseCase(repo)

    @Provides
    fun provideGetNaverAccessTokenUseCase(repo: AccessTokenRepository): GetNaverAccessTokenUseCase =
        GetNaverAccessTokenUseCase(repo)

    @Provides
    fun provideLoginUseCase(repo: LoginRepository): LoginUseCase = LoginUseCase(repo)
}
