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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.cancel
import team.applemango.runnerbe.feature.register.onboard.component.OnboardRouter
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore

@OptIn(ExperimentalAnimationApi::class)
class OnboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                val systemUiController = rememberSystemUiController()
                val navController = rememberAnimatedNavController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                    dataStore.data.collectWithLifecycle(this@OnboardActivity) { preferences ->
                        val terms = preferences[DataStoreKey.Onboard.TermsAllCheck]
                        val year = preferences[DataStoreKey.Onboard.Year]
                        val gender = preferences[DataStoreKey.Onboard.Gender]
                        val job = preferences[DataStoreKey.Onboard.Job]
                        val verifyWithEmail = preferences[DataStoreKey.Onboard.VerifyWithEmail]
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
                            // NPE exception occur
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
                OnboardRouter(navController)
            }
        }
    }
}
