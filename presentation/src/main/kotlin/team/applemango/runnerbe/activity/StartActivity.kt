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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.domain.extension.toMessage
import team.applemango.runnerbe.shared.domain.flowExceptionMessage
import team.applemango.runnerbe.shared.util.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.shared.util.extension.toast
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
                handleException(exception)
            }
            .collectWithLifecycle(this) { exception ->
                handleException(exception)
            }
        vm.loadAllRunningItems {
            isReady = true
        }

        applicationContext
            .dataStore
            .data
            .cancellable()
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
            .collectWithLifecycle(this) { preferences ->
                val isSignedUser = preferences[DataStoreKey.Login.Jwt] != null
                val isSnsLoginDone = preferences[DataStoreKey.Login.Uuid] != null
                val isUnregistered = preferences[DataStoreKey.Onboard.Unregister] == true
                if (isUnregistered) {
                    changeActivityWithAnimation<MainActivity>()
                    return@collectWithLifecycle
                }
                when {
                    isSignedUser -> { // JWT 존재
                        changeActivityWithAnimation<MainActivity>()
                        return@collectWithLifecycle
                    }
                    isSnsLoginDone -> { // SNS 로그인 완료 -> 온보딩 페이지로 이동
                        changeActivityWithAnimation<DFMOnboardActivityAlias>()
                        return@collectWithLifecycle
                    }
                    !isSnsLoginDone -> { // SNS 로그인 하기 전
                        changeActivityWithAnimation<DFMLoginActivityAlias>()
                        return@collectWithLifecycle
                    }
                }
                cancel("state check execute must be once.")
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

    private fun handleException(exception: Throwable) {
        toast(exception.toMessage(), Toast.LENGTH_LONG)
        logeukes(type = LoggerType.E) { exception }
    }
}
