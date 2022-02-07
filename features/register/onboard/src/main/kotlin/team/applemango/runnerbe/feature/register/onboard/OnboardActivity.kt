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
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                val navController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                AnimatedNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.linearGradient(GradientAsset.RegisterCommonBackground))
                        .systemBarsPadding(start = false, end = false)
                        .padding(horizontal = 16.dp),
                    navController = navController,
                    startDestination = Step.Terms.name,
                    enterTransition = { slideInHorizontally(animationSpec = tween(500)) },
                    exitTransition = { slideOutHorizontally(animationSpec = tween(500)) }
                ) {
                    composable(route = Step.Terms.name) {
                        OnboardContent(
                            title = "먼저 이용약관을 읽고\n동의해주세요!",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Birthday.name)
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.Birthday.name) {
                        OnboardContent(
                            title = "츨생년도를 입력해주세요.",
                            subtitle = "정확한 나이는 공개되지 않아요!\n20대 초반, 30대 중반 등으로 표기될 거예요.",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Gender.name)
                            },
                            onBackAction = {
                                navController.popBackStack()
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.Gender.name) {
                        OnboardContent(
                            title = "성별을 선택해주세요.",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Job.name)
                            },
                            onBackAction = {
                                navController.popBackStack()
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.Job.name) {
                        OnboardContent(
                            title = "어떤 직군에서 활동하시나요?",
                            subtitle = "추후 마이페이지에서 수정할 수 있어요!",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Email.name)
                            },
                            onBackAction = {
                                navController.popBackStack()
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.Email.name) {
                        OnboardContent(
                            title = "회사 이메일로\n직장을 인증해주세요.",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Birthday.name)
                            },
                            onBackAction = {
                                navController.popBackStack()
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.EmployeeID.name) {
                        OnboardContent(
                            title = "먼저 이용약관을 읽고\n동의해주세요!",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Birthday.name)
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.EmailDone.name) {
                        OnboardContent(
                            title = "먼저 이용약관을 읽고\n동의해주세요!",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Birthday.name)
                            },
                            content = {}
                        )
                    }
                    composable(route = Step.EmployeeDone.name) {
                        OnboardContent(
                            title = "먼저 이용약관을 읽고\n동의해주세요!",
                            onBottomCTAButtonAction = {
                                navController.navigate(Step.Birthday.name)
                            },
                            content = {}
                        )
                    }
                }
            }
        }
    }
}
