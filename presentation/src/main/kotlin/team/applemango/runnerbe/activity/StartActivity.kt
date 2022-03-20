/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.mvi.StartActivityState
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.android.extension.launchedWhenCreated
import team.applemango.runnerbe.shared.domain.util.flowExceptionMessage
import team.applemango.runnerbe.util.DFMLoginActivityAlias
import team.applemango.runnerbe.util.DFMOnboardActivityAlias

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private companion object {
        const val SplashAnimationDuration = 200L
    }

    private var isReady = false
    private val vm: StartActivityViewModel by viewModels()
    private val rootView by lazy { LinearLayout(this) }

    @Suppress("KotlinConstantConditions") // `!isSnsLoginDone` is always true
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(
            rootView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        vm.exceptionFlow
            .catch { exception ->
                basicExceptionHandler(exception)
            }
            .collectWithLifecycle(this) { exception ->
                basicExceptionHandler(exception)
            }
        vm.observe(
            lifecycleOwner = this,
            state = ::handleState
        )
        vm.loadAllRunningItems()

        launchedWhenCreated {
            val preferences = applicationContext
                .dataStore
                .data
                .catch { exception ->
                    vm.emitException(
                        Exception(
                            flowExceptionMessage(
                                flowName = "StartActivity DataStore",
                                exception = exception
                            )
                        )
                    )
                }
                .first()

            val isSignedUser = preferences[DataStoreKey.Login.Jwt] != null
            val isSnsLoginDone = preferences[DataStoreKey.Login.Uuid] != null
            val isUnregistered = preferences[DataStoreKey.Onboard.Unregister] == true
            if (isUnregistered) {
                changeActivityWithAnimation<MainActivity>()
                return@launchedWhenCreated
            }
            when {
                isSignedUser -> { // JWT 존재
                    changeActivityWithAnimation<MainActivity>()
                    return@launchedWhenCreated
                }
                isSnsLoginDone -> { // SNS 로그인 완료 -> 온보딩 페이지로 이동
                    changeActivityWithAnimation<DFMOnboardActivityAlias>()
                    return@launchedWhenCreated
                }
                !isSnsLoginDone -> { // SNS 로그인 하기 전
                    changeActivityWithAnimation<DFMLoginActivityAlias>()
                    return@launchedWhenCreated
                }
            }
        }

        rootView.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isReady) {
                        rootView.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.animate().run {
                    alpha(0f)
                    scaleX(0f)
                    scaleY(0f)
                    interpolator = AnticipateInterpolator()
                    duration = SplashAnimationDuration
                    withEndAction { splashScreenView.remove() }
                    withLayer()
                    start()
                }
            }
        }
    }

    private fun handleState(state: StartActivityState) {
        if (state == StartActivityState.Loaded) {
            isReady = true
        }
    }
}
