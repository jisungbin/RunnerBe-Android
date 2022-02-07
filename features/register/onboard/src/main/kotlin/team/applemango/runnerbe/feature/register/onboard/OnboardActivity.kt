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
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import team.applemango.runnerbe.feature.register.component.OnboardContent
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.theme.GradientAsset

@ExperimentalAnimationApi
class OnboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                var step by remember { mutableStateOf(Step.Terms) }
                var stepIndex by remember { mutableStateOf(0) }
                val navController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                OnboardContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.RegisterCommonBackground)
                        .systemBarsPadding(start = false, end = false),
                    title = stringResource(
                        when (step) {
                            Step.Terms -> R.string.feature_onboard_title_read_terms
                            Step.Birthday -> R.string.feature_onboard_title_input_year
                            Step.Gender -> R.string.feature_onboard_title_select_gender
                            Step.Job -> R.string.feature_onboard_title_question_job
                            Step.Email -> R.string.feature_onboard_title_verify_with_job_email
                            else -> throw NotImplementedError()
                        }
                    ),
                    subtitle = stringResource(
                        when (step) {
                            Step.Birthday -> R.string.feature_onboard_age_show_description
                            Step.Job -> R.string.feature_onboard_can_edit_on_mypage
                            else -> R.string.empty
                        }
                    ),
                    onBottomCTAButtonAction = {
                        navController.navigate(Step.Birthday.name)
                    }
                ) { modifier ->
                    AnimatedNavHost(
                        modifier = modifier,
                        navController = navController,
                        startDestination = Step.Terms.name,
                        enterTransition = {
                            slideInHorizontally(initialOffsetX = { it })
                        },
                        exitTransition = {
                            slideOutHorizontally(targetOffsetX = { -it })
                        }
                    ) {
                        composable(route = Step.Terms.name) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.LightGray)
                            )
                        }
                        composable(route = Step.Birthday.name) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Blue)
                            )
                        }
                        composable(route = Step.Gender.name) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Green)
                            )
                        }
                        composable(route = Step.Job.name) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Cyan)
                            )
                        }
                        composable(route = Step.Email.name) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}
