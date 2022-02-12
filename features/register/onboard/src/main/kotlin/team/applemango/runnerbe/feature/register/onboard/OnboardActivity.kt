/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardActivity.kt] created by Ji Sungbin on 22. 2. 8. 오전 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import javax.inject.Inject
import kotlinx.coroutines.cancel
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.component.OnboardRouter
import team.applemango.runnerbe.feature.register.onboard.constant.EmailVerifyCode
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.di.ViewModelFactory
import team.applemango.runnerbe.feature.register.onboard.di.component.DaggerViewModelComponent
import team.applemango.runnerbe.feature.register.onboard.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.onboard.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.onboard.di.module.ViewModelModule
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterSideEffect
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterState
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.shared.util.extension.toMessage
import team.applemango.runnerbe.shared.util.extension.toast

@OptIn(ExperimentalAnimationApi::class)
class OnboardActivity : ComponentActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var vm: OnboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerViewModelComponent
            .builder()
            .repositoryModule(RepositoryModule())
            .useCaseModule(UseCaseModule())
            .viewModelModule(ViewModelModule())
            .build()
            .inject(this)

        vm = ViewModelProvider(this, viewModelFactory)[OnboardViewModel::class.java]
        vm.exceptionFlow.collectWithLifecycle(this) { handleException(it) }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()

                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                    vm.observe(
                        lifecycleOwner = this@OnboardActivity,
                        state = ::handleRegisterState,
                        sideEffect = { sideEffect ->
                            handleRegisterSideEffect(navController, sideEffect)
                        }
                    )
                    vm.emailVerifyStateFlow.collectWithLifecycle(this@OnboardActivity) { verified ->
                        if (verified) {
                            applicationContext.dataStore.edit { preferences ->
                                preferences[DataStoreKey.Onboard.VerifyWithEmailDone] = true
                            }
                            // navController.navigate(Step.VerifyWithEmailDone.name)
                        }
                    }
                    applicationContext.dataStore.data.collectWithLifecycle(this@OnboardActivity) { preferences ->
                        val terms = preferences[DataStoreKey.Onboard.TermsAllCheck]
                        val year = preferences[DataStoreKey.Onboard.Year]
                        val gender = preferences[DataStoreKey.Onboard.Gender]
                        val job = preferences[DataStoreKey.Onboard.Job]
                        val verifyWithEmail = preferences[DataStoreKey.Onboard.Email]
                        val verifyWithEmployeeId =
                            preferences[DataStoreKey.Onboard.VerifyWithEmployeeId]
                        val verifyWithEmailDone =
                            preferences[DataStoreKey.Onboard.VerifyWithEmailDone]
                        val verifyWithEmployeeIdRequestDone =
                            preferences[DataStoreKey.Onboard.VerifyWithEmployeeIdRequestDone]
                        val lastStepIndex = listOf(
                            terms,
                            year,
                            gender,
                            job,
                            verifyWithEmail,
                            verifyWithEmployeeId,
                            verifyWithEmailDone,
                            verifyWithEmployeeIdRequestDone
                        ).indexOfLast { it != null }
                        if (lastStepIndex != -1) {
                            // NPE issue
                            // https://github.com/applemango-runnerbe/RunnerBe-Android/issues/16
                            /*(1..lastStepIndex).forEach { backstackIndex ->
                                navController.backQueue.addLast(
                                    NavBackStackEntry.create(
                                        context = this@OnboardActivity,
                                        destination = NavDestination(Step.values()[backstackIndex].name)
                                    )
                                )
                            }*/
                            navController.navigate(Step.values()[lastStepIndex].name)
                        }
                        cancel("step restore execute must be once.")
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    snackbarHost = { snackbarHostState ->
                        SnackbarHost(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(bottom = 30.dp)
                                .padding(horizontal = 30.dp),
                            hostState = snackbarHostState
                        ) { snackbarData ->
                            Snackbar(backgroundColor = ColorAsset.G5) {
                                Text(
                                    text = snackbarData.message,
                                    style = Typography.Body14R.copy(color = ColorAsset.Primary)
                                )
                            }
                        }
                    }
                ) { // Scaffold 는 backgroundColor 로 Brush 가 안되서 이렇게 함
                    OnboardRouter(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = GradientAsset.RegisterCommonBackground)
                            /*.statusBarsPadding()
                            .navigationBarsWithImePadding() // Step.VerifyWithEmail 단계에 TextField 있음*/
                            .systemBarsPadding(start = false, end = false)
                            .padding(horizontal = 16.dp),
                        navController = navController,
                        scaffoldState = scaffoldState,
                        vm = vm,
                    )
                }
            }
        }
    }

    private fun handleRegisterState(state: RegisterState) {
        val message = when (state) {
            RegisterState.None -> {
                StringAsset.Empty
            }
            RegisterState.NullInformation -> {
                StringAsset.Toast.RegisterNullInformation
            }
            RegisterState.Success -> {
                StringAsset.Toast.RegisterSuccess
            }
            RegisterState.Request -> {
                StringAsset.Toast.RegisterRequest
            }
        }
        if (message.isNotEmpty()) {
            toast(message)
        }
    }

    private suspend fun handleRegisterSideEffect(
        navController: NavHostController,
        sideEffect: RegisterSideEffect,
    ) {
        when (sideEffect) {
            is RegisterSideEffect.NavigateToNextStep -> {
                navController.navigate(sideEffect.nextStep.name)
            }
            RegisterSideEffect.ResetStep -> {
                applicationContext.dataStore.edit { preferences ->
                    preferences.clear() // clear all preferences
                }
            }
        }
    }

    private fun handleException(exception: Throwable) {
        toast(exception.toMessage(), Toast.LENGTH_LONG)
        logeukes(type = LoggerType.E) { exception }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.data.toString().contains(EmailVerifyCode)) {
            vm.updateEmailVerifyState(true)
        } else {
            toast(StringAsset.Toast.VerifyEmailFailure)
        }
    }
}
