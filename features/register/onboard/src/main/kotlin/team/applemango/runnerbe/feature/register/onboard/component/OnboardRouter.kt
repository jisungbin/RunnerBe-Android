/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardRouter.kt] created by Ji Sungbin on 22. 2. 9. 오후 2:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.feature.home.board.BoardActivity
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.step.AgePicker
import team.applemango.runnerbe.feature.register.onboard.step.TermsTable
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.activity.util.DFMLoginActivityAlias
import team.applemango.runnerbe.shared.compose.util.presentationDrawableOf
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.util.extension.dataStore

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun OnboardRouter(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    var enableGoNextStep by remember { mutableStateOf(false) }
    var stepIndex by remember { mutableStateOf(0) }
    var stepIndexString by remember { mutableStateOf("") }

    if (stepIndex != 0) {
        stepIndexString = "$stepIndex/4"
    }

    stepIndex = when (navController.currentBackStackEntryAsState().value?.destination?.route) {
        Step.Terms.name -> 0
        Step.Birthday.name -> 1
        Step.Gender.name -> 2
        Step.Job.name -> 3
        Step.VerifyWithEmail.name, Step.VerifyWithEmployeeId.name -> 4
        Step.VerifyWithEmailDone.name, Step.VerifyWithEmployeeIdRequestDone.name -> 0
        else -> stepIndex
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
                modifier = Modifier.clickable { // < 뒤로가기
                    if (!navController.popBackStack()) {
                        // 뒤로 갈 수 있는 백스택이 없다면 로그인 화면으로 돌아가야 함
                        // SnsLoginActivity 에서 백스택을 남기고
                        // 여기서 바로 finish 를 통해 뒤로 가게 된다면 가능 하지만,
                        // 만약 로그인만 완료한 상태에서 앱을 종료해서 바로 이 화면으로 오게 된다면
                        // finish 를 했을 때 백스택이 없어서 앱 자체가 닫힘
                        // 따라서 SnsLoginActivity 와 OnboardRouter 둘 다 백스택을 없애고
                        // 개별 startActivity 를 호출하는 식으로 구성
                        activity.changeActivityWithAnimation<DFMLoginActivityAlias>()
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
                    text = stepIndexString,
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
                        context.dataStore.edit { preferences ->
                            preferences[DataStoreKey.Onboard.TermsAllCheck] = true
                        }
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
                    AgePicker()
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
                            .background(color = Color(0xFFA5D6A7))
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
                            .background(color = Color(0xFFE6EE9C))
                    )
                }
            }
            composable(route = Step.VerifyWithEmail.name) { // 회사 이메일로 직장 인증
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
                // 무조건 Step.VerifyWithEmail 을 거쳐야 이 step 으로 올 수 있음
                OnboardContent(
                    step = Step.VerifyWithEmployeeId,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 인증하기
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
                    onBottomCTAButtonAction = { // 메인 화면으로
                        activity.changeActivityWithAnimation<BoardActivity>()
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
                    step = Step.VerifyWithEmployeeIdRequestDone,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 메인 화면으로
                        activity.changeActivityWithAnimation<BoardActivity>()
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
