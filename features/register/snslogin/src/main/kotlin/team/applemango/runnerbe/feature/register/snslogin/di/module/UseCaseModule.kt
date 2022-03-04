/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.di.module

import team.applemango.runnerbe.domain.register.runnerbe.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.register.runnerbe.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.domain.register.runnerbe.usecase.LoginUseCase

internal class UseCaseModule(private val repositoryModule: RepositoryModule) {
    fun provideGetKakaoAccessTokenUseCase() =
        GetKakaoAccessTokenUseCase(repositoryModule.provideAccessTokenRepository())

    fun provideGetNaverAccessTokenUseCase() =
        GetNaverAccessTokenUseCase(repositoryModule.provideAccessTokenRepository())

    fun provideLoginUseCase() = LoginUseCase(repositoryModule.getLoginRepository)
}
