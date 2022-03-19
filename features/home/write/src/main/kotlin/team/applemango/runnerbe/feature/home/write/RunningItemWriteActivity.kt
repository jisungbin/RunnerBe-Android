/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWrtieActivity.kt] created by Ji Sungbin on 22. 3. 18. 오전 6:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.home.write.component.RunningItemWrite
import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState
import team.applemango.runnerbe.shared.base.WindowInsetsActivity
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.extension.finishWithAnimation

@AndroidEntryPoint
class RunningItemWriteActivity : WindowInsetsActivity() {

    private val vm: RunningItemWriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberSystemUiController()

            LaunchedEffect(Unit) {
                systemUiController.setSystemBarsColor(Color.Transparent)
                vm.exceptionFlow.collectWithLifecycle(
                    lifecycleOwner = this@RunningItemWriteActivity,
                    action = { exception ->
                        basicExceptionHandler(exception)
                    }
                )
                vm.observe(
                    lifecycleOwner = this@RunningItemWriteActivity,
                    state = ::handleRunningItemWriteState,
                )
            }

            RunningItemWrite(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = GradientAsset.BlackGradientBrush)
                    .systemBarsPadding()
            )
        }
    }

    private fun handleRunningItemWriteState(state: RunningItemWriteState) {
        if (state == RunningItemWriteState.Done) {
            finishWithAnimation()
        }
    }
}
