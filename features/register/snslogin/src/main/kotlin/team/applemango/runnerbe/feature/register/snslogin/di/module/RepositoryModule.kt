/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import team.applemango.runnerbe.data.login.repository.KakaoAccessTokenRepositoryImpl
import team.applemango.runnerbe.domain.repository.AccessTokenRepository
import javax.inject.Singleton

@Module
internal class RepositoryModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideAccessTokenRepository(): AccessTokenRepository =
        KakaoAccessTokenRepositoryImpl(context)
}
