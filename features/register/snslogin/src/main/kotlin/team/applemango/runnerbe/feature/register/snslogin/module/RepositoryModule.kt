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
import team.applemango.runnerbe.data.login.repository.KakaoLoginRepositoryImpl
import team.applemango.runnerbe.data.login.repository.NaverLoginRepositoryImpl
import team.applemango.runnerbe.domain.repository.LoginRepository
import team.applemango.runnerbe.feature.register.snslogin.qualifier.Kakao
import team.applemango.runnerbe.feature.register.snslogin.qualifier.Naver

@Module
class RepositoryModule(private val context: Context) {
    @Provides
    @Kakao
    fun provideKakaoLoginRepository(): LoginRepository = KakaoLoginRepositoryImpl(context)

    @Provides
    @Naver
    fun provideNaverLoginRepository(): LoginRepository = NaverLoginRepositoryImpl(context)
}
