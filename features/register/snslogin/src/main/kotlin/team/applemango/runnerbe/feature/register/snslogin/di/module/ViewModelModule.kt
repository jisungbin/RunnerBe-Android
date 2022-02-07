/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ViewModelModule.kt] created by Ji Sungbin on 22. 2. 6. 오후 2:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.di.module

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import team.applemango.runnerbe.domain.usecase.GetKakaoAccessTokenUseCase
import team.applemango.runnerbe.domain.usecase.GetNaverAccessTokenUseCase
import team.applemango.runnerbe.feature.register.snslogin.SnsLoginViewModel
import team.applemango.runnerbe.feature.register.snslogin.di.qualifier.ViewModelKey

@Module
class ViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(SnsLoginViewModel::class)
    fun provideSnsLoginViewModel(
        getKakaoKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
        getNaverKakaoAccessTokenUseCase: GetNaverAccessTokenUseCase,
    ): ViewModel {
        return SnsLoginViewModel(
            getKakaoKakaoAccessTokenUseCase = getKakaoKakaoAccessTokenUseCase,
            getNaverKakaoAccessTokenUseCase = getNaverKakaoAccessTokenUseCase
        )
    }
}
