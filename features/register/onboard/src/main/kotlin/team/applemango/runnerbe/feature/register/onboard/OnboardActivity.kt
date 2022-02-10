/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardActivity.kt] created by Ji Sungbin on 22. 2. 8. 오전 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import javax.inject.Inject
import kotlinx.coroutines.cancel
import team.applemango.runnerbe.feature.register.onboard.component.OnboardRouter
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.di.ViewModelFactory
import team.applemango.runnerbe.feature.register.onboard.di.component.DaggerViewModelComponent
import team.applemango.runnerbe.feature.register.onboard.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.onboard.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.onboard.di.module.ViewModelModule
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
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

        initDynamicLink()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                val navController = rememberAnimatedNavController()
                logeukes { navController.currentBackStackEntryAsState().value }
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                    vm.emailVerifyStateFlow.collectWithLifecycle(this@OnboardActivity) { verified ->
                        if (verified) {
                            applicationContext.dataStore.edit { preferences ->
                                preferences[DataStoreKey.Onboard.VerifyWithEmailDone] = true
                            }
                            navController.navigate(Step.VerifyWithEmailDone.name)
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
                OnboardRouter(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.RegisterCommonBackground)
                        .systemBarsPadding(start = false, end = false)
                        .padding(horizontal = 16.dp),
                    navController = navController,
                    vm = vm
                )
            }
        }
    }

    private fun handleException(exception: Throwable) {
        toast(exception.toMessage(), Toast.LENGTH_LONG)
        logeukes(type = LoggerType.E) { exception }
    }

    private fun initDynamicLink() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link.toString()
                    if (deepLink.contains("verify%3Dtrue")) { // verify=true
                        vm.updateEmailVerifyState(true)
                    }
                }
            }
            .addOnFailureListener(this) {
                logeukes { "getDynamicLink:onFailure: $it" }
            }
    }
}
