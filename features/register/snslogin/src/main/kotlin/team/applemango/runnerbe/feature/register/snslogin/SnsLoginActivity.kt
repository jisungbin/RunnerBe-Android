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
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import javax.inject.Inject
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.register.snslogin.component.SnsLoginScreen
import team.applemango.runnerbe.feature.register.snslogin.di.ViewModelFactory
import team.applemango.runnerbe.feature.register.snslogin.di.component.DaggerViewModelComponent
import team.applemango.runnerbe.feature.register.snslogin.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.snslogin.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.snslogin.di.module.ViewModelModule
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginSideEffect
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginState
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.shared.util.extension.launchedWhenCreated
import team.applemango.runnerbe.shared.util.extension.toast
import team.applemango.runnerbe.util.DFMOnboardActivityAlias

class SnsLoginActivity : ComponentActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var vm: SnsLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerViewModelComponent
            .builder()
            .repositoryModule(RepositoryModule(this)) // must activity context (startActivity calling)
            .useCaseModule(UseCaseModule())
            .viewModelModule(ViewModelModule())
            .build()
            .inject(this)

        vm = ViewModelProvider(this, viewModelFactory)[SnsLoginViewModel::class.java]
        vm.observe(lifecycleOwner = this, state = ::handleState, sideEffect = ::handleSideEffect)
        vm.exceptionFlow.collectWithLifecycle(this) { handleException(it) }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                SnsLoginScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.RegisterCommonBackground)
                        .systemBarsPadding(start = false, end = false)
                        .padding(horizontal = 16.dp),
                    vm = vm
                )
            }
        }
    }

    // 백스택 안 남긴 이유는 OnboardRouter 뒤로가기 버튼 clickable modifier 주석 참고
    private fun handleState(state: LoginState) {
        if (state.success) {
            changeActivityWithAnimation<DFMOnboardActivityAlias>()
        }
    }

    private fun handleSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is LoginSideEffect.SaveUuid -> {
                launchedWhenCreated {
                    applicationContext.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Login.Uuid] = sideEffect.uuid
                    }
                }
            }
        }
    }

    // TODO: handle exception in debug mode
    private fun handleException(exception: Throwable) {
        toast(exception.toString(), Toast.LENGTH_LONG)
        logeukes(type = LoggerType.E) { exception }
    }
}
