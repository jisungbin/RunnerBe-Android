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
import android.widget.Toast
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
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.register.snslogin.component.SnsLoginScreen
import team.applemango.runnerbe.feature.register.snslogin.constant.LoginState
import team.applemango.runnerbe.feature.register.snslogin.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.snslogin.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginSideEffect
import team.applemango.runnerbe.shared.base.WindowInsetActivity
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.domain.extension.toMessage
import team.applemango.runnerbe.shared.util.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.shared.util.extension.launchedWhenCreated
import team.applemango.runnerbe.shared.util.extension.toast
import team.applemango.runnerbe.util.DFMOnboardActivityAlias

class SnsLoginActivity : WindowInsetActivity() {

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

        vm.observe(lifecycleOwner = this, state = ::handleState, sideEffect = ::handleSideEffect)
        vm.exceptionFlow.collectWithLifecycle(this) { exception ->
            handleException(exception)
        }

        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                SnsLoginScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.BlackGradientBrush)
                        .systemBarsPadding(start = false, end = false)
                        .padding(horizontal = 16.dp),
                    vm = vm
                )
            }
        }
    }

    // 백스택 안 남긴 이유는 OnboardRouter 뒤로가기 버튼 clickable modifier 주석 참고
    private fun handleState(state: LoginState) {
        when (state) {
            LoginState.Done -> changeActivityWithAnimation<DFMOnboardActivityAlias>()
            LoginState.Registered -> /*changeActivityWithAnimation<MainActivity>()*/ TODO() // TODO
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

    private fun handleException(exception: Throwable) {
        toast(exception.toMessage(), Toast.LENGTH_LONG)
        logeukes(type = LoggerType.E) { exception }
    }
}
