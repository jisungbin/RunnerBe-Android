/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardRouter.kt] created by Ji Sungbin on 22. 2. 9. 오후 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component

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
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.step.TermsTable
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.compose.ColorAsset
import team.applemango.runnerbe.shared.compose.GradientAsset
import team.applemango.runnerbe.shared.compose.Typography

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun OnboardRouter() {
    var enableGoNextStep by remember { mutableStateOf(false) }
    var stepIndex by remember { mutableStateOf(0) }
    val navController = rememberAnimatedNavController()

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
                modifier = Modifier.clickable { // < 뒤로가기
                    if (navController.popBackStack()) {
                        finish()
                    }
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
                modifier = Modifier.clickable { // X 온보딩 건너뛰기
                    // TODO: goto main activity
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
                        applicationContext.dataStore.edit { preferences ->
                            preferences[DataStoreKey.Onboard.TermsAllCheck] = true
                        }
                        stepIndex = 1
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
                        stepIndex = 2
                        navController.navigate(Step.Gender.name)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF80CBC4))
                    )
                }
            }
            composable(route = Step.Gender.name) {
                OnboardContent(
                    step = Step.Gender,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = {
                        stepIndex = 3
                        navController.navigate(Step.Job.name)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFFA5D6A7))
                    )
                }
            }
            composable(route = Step.Job.name) {
                OnboardContent(
                    step = Step.Job,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = {
                        stepIndex = 4
                        navController.navigate(Step.VerifyWithEmail.name)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFFE6EE9C))
                    )
                }
            }
            composable(route = Step.VerifyWithEmail.name) {
                OnboardContent(
                    step = Step.VerifyWithEmail,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 회사 이메일이 없어요
                        navController.navigate(Step.VerifyWithEmployeeId.name)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFFFFE082))
                    )
                }
            }
            composable(route = Step.VerifyWithEmployeeId.name) { // 사원증으로 인증
                OnboardContent(
                    step = Step.VerifyWithEmailDone,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 인증하기
                        stepIndex = 0
                        navController.navigate(Step.VerifyWithEmployeeIdRequestDone.name)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFFFFAB91))
                    )
                }
            }
            composable(route = Step.VerifyWithEmailDone.name) { // 이메일 인증 완료
                OnboardContent(
                    step = Step.VerifyWithEmailDone,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = {
                        navController.navigate(Step.Terms.name)
                    }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "️\uD83C\uDF89", // ️🎉
                        style = LocalTextStyle.current.copy(fontSize = 110.sp)
                    )
                }
            }
            composable(route = Step.VerifyWithEmployeeIdRequestDone.name) { // 사원증 제출 완료
                OnboardContent(
                    step = Step.VerifyWithEmailDone,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 인증하기
                        stepIndex = 0
                        navController.navigate(Step.VerifyWithEmployeeIdRequestDone.name)
                    }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "️\uD83D\uDE18", // ️😘
                        style = LocalTextStyle.current.copy(fontSize = 110.sp)
                    )
                }
            }
        }
    }
}
