/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginActivity.kt] created by Ji Sungbin on 22. 2. 5. 오후 5:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.register.snslogin.component.SnsLoginScreen
import team.applemango.runnerbe.feature.register.snslogin.constant.LoginState
import team.applemango.runnerbe.feature.register.snslogin.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.snslogin.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginSideEffect
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.android.extension.launchedWhenCreated
import team.applemango.runnerbe.shared.android.extension.setWindowInsets
import team.applemango.runnerbe.shared.compose.extension.verticalInsetsPadding
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.util.DFMOnboardActivityAlias
import team.applemango.runnerbe.util.MainActivityAlias

class SnsLoginActivity : ComponentActivity() {

    private val vm: SnsLoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repositoryModule = RepositoryModule(this@SnsLoginActivity)
                val useCaseModule = UseCaseModule(repositoryModule)
                return SnsLoginViewModel(
                    getKakaoAccessTokenUseCase = useCaseModule.provideGetKakaoAccessTokenUseCase(),
                    getNaverAccessTokenUseCase = useCaseModule.provideGetNaverAccessTokenUseCase(),
                    loginUseCase = useCaseModule.provideLoginUseCase()
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowInsets()
        vm.observe(lifecycleOwner = this, state = ::handleState, sideEffect = ::handleSideEffect)
        vm.exceptionFlow.collectWithLifecycle(this) { exception ->
            basicExceptionHandler(exception)
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            LaunchedEffect(Unit) {
                systemUiController.setSystemBarsColor(color = Color.Transparent)
            }
            SnsLoginScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = GradientAsset.BlackGradientBrush)
                    .verticalInsetsPadding()
                    .padding(horizontal = 16.dp),
                vm = vm
            )
        }
    }

    // 백스택 안 남긴 이유는 OnboardRouter 뒤로가기 버튼 clickable modifier 주석 참고
    private fun handleState(state: LoginState) {
        when (state) {
            LoginState.Done -> changeActivityWithAnimation<DFMOnboardActivityAlias>()
            LoginState.Registered -> changeActivityWithAnimation<MainActivityAlias>()
            LoginState.None -> return
        }
    }

    private fun handleSideEffect(sideEffect: LoginSideEffect) {
        launchedWhenCreated {
            when (sideEffect) {
                is LoginSideEffect.SaveJwt -> {
                    applicationContext.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Login.Jwt] = sideEffect.jwt
                    }
                }
                is LoginSideEffect.SaveUuid -> {
                    applicationContext.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Login.Uuid] = sideEffect.uuid
                    }
                }
            }
        }
    }
}
