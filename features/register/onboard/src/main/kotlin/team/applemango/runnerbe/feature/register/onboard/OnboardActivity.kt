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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.feature.register.component.OnboardContent
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.step.TermsTable
import team.applemango.runnerbe.shared.util.presentationDrawableOf
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.GradientAsset
import team.applemango.runnerbe.theme.Typography

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
            ProvideWindowInsets {
                var enableGoNextStep by remember { mutableStateOf(false) }
                var stepIndex by remember { mutableStateOf(0) }
                val navController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.RegisterCommonBackground)
                        .systemBarsPadding(start = false, end = false)
                        .padding(horizontal = 16.dp)
                ) {
                    Row( // TopBar
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            modifier = Modifier.clickable {
                                // onBackAction()
                            },
                            painter = rememberDrawablePainter(presentationDrawableOf("ic_round_arrow_left_24")),
                            contentDescription = null
                        )
                        AnimatedVisibility(
                            visible = stepIndex != 0,
                            enter = fadeIn(tween(500)),
                            exit = fadeOut(tween(500))
                        ) {
                            Text(
                                text = "$stepIndex/4",
                                style = Typography.Body16R.copy(color = ColorAsset.G3)
                            )
                        }
                        Image(
                            modifier = Modifier.clickable {
                                finish() // TODO: goto main activity
                            },
                            painter = rememberDrawablePainter(presentationDrawableOf("ic_round_close_24")),
                            contentDescription = null
                        )
                    }
                    AnimatedNavHost( // main content + bottom cta button
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = Step.Terms.name,
                        enterTransition = { fadeIn(tween(500)) },
                        exitTransition = { fadeOut(tween(500)) }
                    ) {
                        composable(route = Step.Terms.name) {
                            OnboardContent(
                                step = Step.Terms,
                                bottomCTAButtonEnabled = enableGoNextStep,
                                onBottomCTAButtonAction = {
                                    navController.navigate(Step.Birthday.name)
                                }
                            ) {
                                TermsTable(onAllTermsCheckStateChanged = { allChecked ->
                                    enableGoNextStep = allChecked
                                })
                            }
                        }
                        composable(route = Step.Birthday.name) {
                            OnboardContent(
                                step = Step.Birthday,
                                bottomCTAButtonEnabled = true,
                                onBottomCTAButtonAction = {
                                    navController.navigate(Step.Gender.name)
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFA9D1F7))
                                )
                            }
                        }
                        composable(route = Step.Gender.name) {
                            OnboardContent(
                                step = Step.Gender,
                                bottomCTAButtonEnabled = true,
                                onBottomCTAButtonAction = {
                                    navController.navigate(Step.Job.name)
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFB4F0A7))
                                )
                            }
                        }
                        composable(route = Step.Job.name) {
                            OnboardContent(
                                step = Step.Job,
                                bottomCTAButtonEnabled = true,
                                onBottomCTAButtonAction = {
                                    navController.navigate(Step.VerifyWithEmail.name)
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFFFFFBF))
                                )
                            }
                        }
                        composable(route = Step.VerifyWithEmail.name) {
                            OnboardContent(
                                step = Step.VerifyWithEmail,
                                bottomCTAButtonEnabled = true,
                                onBottomCTAButtonAction = {
                                    navController.navigate(Step.EmailVerifyDone.name)
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFFFDFBE))
                                )
                            }
                        }
                        composable(route = Step.EmailVerifyDone.name) {
                            OnboardContent(
                                step = Step.EmailVerifyDone,
                                bottomCTAButtonEnabled = true,
                                onBottomCTAButtonAction = {
                                    navController.navigate(Step.Terms.name)
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFFFB1B0))
                                )
                            }
                        }
                        composable(route = Step.VerifyWithEmployeeId.name) {
                        }
                        composable(route = Step.EmployeeIdVerifyRequestDone.name) {
                        }
                    }
                }
            }
        }
    }
}
