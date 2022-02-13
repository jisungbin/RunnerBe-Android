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
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.skydoves.landscapist.rememberDrawablePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.home.board.MainActivity
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.feature.register.onboard.step.EmailVerify
import team.applemango.runnerbe.feature.register.onboard.step.EmployeeIdVerify
import team.applemango.runnerbe.feature.register.onboard.step.GenderPicker
import team.applemango.runnerbe.feature.register.onboard.step.JobPicker
import team.applemango.runnerbe.feature.register.onboard.step.TermsTable
import team.applemango.runnerbe.feature.register.onboard.step.YearPicker
import team.applemango.runnerbe.shared.compose.extension.presentationDrawableOf
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.shared.util.extension.toast
import team.applemango.runnerbe.util.DFMLoginActivityAlias

private var lastBackPressedTime = 0L

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun OnboardRouter(
    modifier: Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    vm: OnboardViewModel,
) {
    val context = LocalContext.current
    val activity = context as Activity
    val coroutineScope = rememberCoroutineScope()
    var stepIndex by remember { mutableStateOf(0) }
    var stepIndexString by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf<Bitmap?>(null) }
    var enableGoNextStep by remember { mutableStateOf(false) }

    stepIndex = when (navController.currentBackStackEntryAsState().value?.destination?.route) {
        Step.Terms.name -> 0
        Step.Year.name -> 1
        Step.Gender.name -> 2
        Step.Job.name -> 3
        Step.VerifyWithEmail.name, Step.VerifyWithEmployeeId.name -> 4
        Step.VerifyWithEmailDone.name, Step.VerifyWithEmployeeIdRequestDone.name -> 0
        else -> stepIndex
    }

    if (stepIndex != 0) {
        stepIndexString = "$stepIndex/4"
    }

    fun confirmFinish() {
        val nowBackPressedTime = System.currentTimeMillis()
        if (nowBackPressedTime - lastBackPressedTime <= 2000) { // 2로 내에 다시 누름
            activity.finish()
        } else {
            coroutineScope.launch {
                lastBackPressedTime = nowBackPressedTime
                scaffoldState.snackbarHostState.showSnackbar(StringAsset.Snackbar.ConfirmFinish)
            }
            // snackbarHostState.showSnackbar keeps the coroutine scope in the suspended state until the snackbar is dismissed.
            coroutineScope.launch {
                delay(2000)
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    Column(modifier = modifier) {
        Crossfade(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            targetState = stepIndex != 0
        ) { showTopBar ->
            when (showTopBar) {
                true -> {
                    Row( // TopBar
                        modifier = Modifier.fillMaxSize(),
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
                        Text(
                            text = stepIndexString,
                            style = Typography.Body16R.copy(color = ColorAsset.G3)
                        )
                        Image(
                            modifier = Modifier.clickable { // X 온보딩 건너뛰기
                                // TODO: Dialog
                                toast(context, "todo: dialog")
                                context.changeActivityWithAnimation<MainActivity>()
                            },
                            painter = rememberDrawablePainter(presentationDrawableOf("ic_round_close_24")),
                            contentDescription = null
                        )
                    }
                }
                else -> {
                    Spacer(modifier = Modifier.fillMaxSize())
                }
            }
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
                        coroutineScope.launch {
                            context.dataStore.edit { preferences ->
                                preferences[DataStoreKey.Onboard.TermsAllCheck] = true
                            }
                        }
                        navController.navigate(Step.Year.name)
                    }
                ) {
                    TermsTable(onAllTermsCheckStateChanged = { allChecked ->
                        enableGoNextStep = allChecked
                    })
                }
            }
            composable(route = Step.Year.name) {
                OnboardContent(
                    step = Step.Year,
                    bottomCTAButtonEnabled = enableGoNextStep,
                    onBottomCTAButtonAction = {
                        navController.navigate(Step.Gender.name)
                    }
                ) {
                    YearPicker(selectedYearChanged = { isAdult ->
                        enableGoNextStep = isAdult
                    })
                }
            }
            composable(route = Step.Gender.name) {
                OnboardContent(
                    step = Step.Gender,
                    bottomCTAButtonEnabled = enableGoNextStep,
                    onBottomCTAButtonAction = {
                        navController.navigate(Step.Job.name)
                    }
                ) {
                    GenderPicker(genderSelectChanged = { isSelected ->
                        enableGoNextStep = isSelected
                    })
                }
            }
            composable(route = Step.Job.name) {
                OnboardContent(
                    step = Step.Job,
                    bottomCTAButtonEnabled = enableGoNextStep,
                    onBottomCTAButtonAction = {
                        navController.navigate(Step.VerifyWithEmail.name)
                    }
                ) {
                    JobPicker(jobSelectChanged = { isSelected ->
                        enableGoNextStep = isSelected
                    })
                }
            }
            composable(route = Step.VerifyWithEmail.name) { // 회사 이메일로 직장 인증
                // 링크 클릭시 딥링크로 OnboardActivity 이동
                // OnboardActivity 에서 회원기입 요청
                OnboardContent(
                    step = Step.VerifyWithEmail,
                    bottomCTAButtonEnabled = true, // 회사 이메일이 없어요
                    onBottomCTAButtonAction = {
                        navController.navigate(Step.VerifyWithEmployeeId.name)
                    }
                ) {
                    EmailVerify(vm = vm)
                }
            }
            composable(route = Step.VerifyWithEmployeeId.name) { // 사원증으로 인증
                // 무조건 Step.VerifyWithEmail 을 거쳐야 이 step 으로 올 수 있음
                OnboardContent(
                    step = Step.VerifyWithEmployeeId,
                    bottomCTAButtonEnabled = photo != null,
                    onBottomCTAButtonAction = { // 인증하기
                        coroutineScope.launch {
                            vm.registerUser(
                                dataStore = context.dataStore,
                                photo = photo!!, // non null, 만약 null 로 들어오면 작동되지 않아야 하기 때문에 NonNull 강제 처리
                                nextStep = Step.VerifyWithEmployeeIdRequestDone,
                                // isTestMode = true
                            )
                        }
                    }
                ) {
                    EmployeeIdVerify(photo = photo, onPhotoChanged = { newPhoto ->
                        photo = newPhoto
                    })
                }
            }
            composable(route = Step.VerifyWithEmailDone.name) { // 이메일 인증 완료
                BackHandler {
                    confirmFinish()
                }
                LaunchedEffect(Unit) {
                    context.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Onboard.VerifyWithEmailDone] = true
                    }
                }
                OnboardContent(
                    step = Step.VerifyWithEmailDone,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 메인 화면으로
                        activity.changeActivityWithAnimation<MainActivity>()
                    }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "️\uD83C\uDF89", // ️🎉
                        style = LocalTextStyle.current.copy(fontSize = 100.sp)
                    )
                }
            }
            composable(route = Step.VerifyWithEmployeeIdRequestDone.name) { // 사원증 제출 완료
                BackHandler {
                    confirmFinish()
                }
                LaunchedEffect(Unit) {
                    context.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Onboard.VerifyWithEmployeeIdRequestDone] = true
                    }
                }
                OnboardContent(
                    step = Step.VerifyWithEmployeeIdRequestDone,
                    bottomCTAButtonEnabled = true,
                    onBottomCTAButtonAction = { // 메인 화면으로
                        activity.changeActivityWithAnimation<MainActivity>()
                    }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "️\uD83D\uDCD1", // 📑
                        style = LocalTextStyle.current.copy(fontSize = 100.sp)
                    )
                }
            }
        }
    }
}
