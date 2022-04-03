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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.component.OnboardRouter
import team.applemango.runnerbe.feature.register.onboard.constant.EmailVerifyCode
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.onboard.mvi.RegisterSideEffect
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.android.extension.toast
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.extension.verticalInsetsPadding
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

class OnboardActivity : ComponentActivity() {

    private val vm: OnboardViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OnboardViewModel(
                    checkUsableEmailUseCase = UseCaseModule.provideCheckUsableEmailUseCase(),
                    userRegisterUseCase = UseCaseModule.provideUserRegisterUseCase(),
                    mailjetSendUseCase = UseCaseModule.provideMailSendUseCase(),
                    imageUploadUseCase = UseCaseModule.provideImageUploadUseCase()
                ) as T
            }
        }
    }

    @OptIn(
        ExperimentalAnimationApi::class, // rememberAnimatedNavController
        LocalActivityUsageApi::class // LocalActivity
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowInsetsUsage()
        vm.exceptionFlow
            .defaultCatch(action = ::basicExceptionHandler)
            .collectWithLifecycle(this) { exception ->
                basicExceptionHandler(exception)
            }

        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberAnimatedNavController()

                LaunchedEffect(Unit) {
                    vm.observe(
                        lifecycleOwner = this@OnboardActivity,
                        state = ::handleState,
                        sideEffect = { sideEffect ->
                            handleRegisterSideEffect(navController, sideEffect)
                        }
                    )
                    // 이메일 인증 됨 -> photo null 로 회원가입 진행
                    vm.emailVerifyStateFlow
                        .defaultCatch(action = vm::emitException)
                        .collectWithLifecycle(this@OnboardActivity) { verified ->
                            if (verified) {
                                vm.registerUser(
                                    dataStore = applicationContext.dataStore,
                                    photo = null,
                                    nextStep = Step.VerifyWithEmailDone
                                )
                            }
                        }
                    // 이메일 인증 임시 비활성화
                    val preferences = applicationContext
                        .dataStore
                        .data
                        .defaultCatch(action = vm::emitException)
                        .first()
                    val terms = preferences[DataStoreKey.Onboard.TermsAllCheck]
                    val year = preferences[DataStoreKey.Onboard.Year]
                    val gender = preferences[DataStoreKey.Onboard.Gender]
                    val job = preferences[DataStoreKey.Onboard.Job]
                    // val verifyWithEmail = preferences[DataStoreKey.Onboard.Email]
                    val lastStepIndex = listOf(
                        terms,
                        year,
                        gender,
                        job,
                        // verifyWithEmail,
                    ).indexOfLast { step ->
                        step != null
                    }
                    if (lastStepIndex != -1) {
                        // NPE occurred
                        // TODO: 백스택 임의 생성
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
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    snackbarHost = { snackbarHostState ->
                        SnackbarHost(
                            modifier = Modifier
                                .verticalInsetsPadding()
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
                ) { // Scaffold 는 backgroundColor 로 brush 가 안되서 이렇게 함
                    OnboardRouter(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = GradientAsset.Background.Brush)
                            .verticalInsetsPadding()
                            .padding(horizontal = 16.dp),
                        navController = navController,
                        scaffoldState = scaffoldState,
                        vm = vm
                    )
                }
            }
        }
    }

    private fun handleState(state: RegisterState) {
        val message = when (state) {
            RegisterState.None -> {
                EmptyString
            }
            RegisterState.ImageUploading -> {
                StringAsset.Toast.ImageUploading
            }
            RegisterState.ImageUploadError -> {
                StringAsset.Toast.ImageUploadError
            }
            RegisterState.VerifyRequestDone -> {
                StringAsset.Toast.EmployeeIdRegisterRequestDone
            }
            RegisterState.RegisterDone -> {
                StringAsset.Toast.RegisterSuccess
            }
            RegisterState.DuplicateUuid -> {
                StringAsset.Toast.DuplicateUuid
            }
            RegisterState.DuplicateEmail -> {
                StringAsset.Toast.DuplicateEmail
            }
            RegisterState.DuplicateNickname -> {
                StringAsset.Toast.DuplicateNickname
            }
            RegisterState.DatabaseError -> {
                StringAsset.Toast.DatabaseError
            }
            RegisterState.NullInformation -> {
                StringAsset.Toast.RegisterNullInformation
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
            RegisterSideEffect.ResetStep -> { // 회원가입 단계 초기화
                applicationContext.dataStore.edit { preferences ->
                    preferences.clear() // clear all preferences
                }
            }
            is RegisterSideEffect.SaveUserJwt -> {
                applicationContext.dataStore.edit { preference ->
                    preference[DataStoreKey.Login.Jwt] = sideEffect.jwt
                }
            }
            is RegisterSideEffect.NavigateToNextStep -> {
                navController.navigate(sideEffect.nextStep.name)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logeukes { intent?.data?.getQueryParameter("verify") }
        if (intent?.data.toString().contains(EmailVerifyCode)) {
            vm.updateEmailVerifyState(true)
        } else {
            toast(StringAsset.Toast.VerifyEmailFailure)
        }
    }
}
