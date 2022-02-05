/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.module

import dagger.Module
import dagger.Provides

/*@Module
class RepositoryModule {
    @Provides
    @Kakao
    fun provideKakaoLoginRepository(
        @ApplicationContext context: Context,
    ): LoginRepository = KakaoLoginRepositoryImpl(context)

    @Provides
    @Naver
    fun provideNaverLoginRepository(
        @ApplicationContext context: Context,
    ): LoginRepository = NaverLoginRepositoryImpl(context)
}*/

@Module
class RepositoryModule {
    @Provides
    fun provideA(): A = A()
}

class A(val b: String = "b")
