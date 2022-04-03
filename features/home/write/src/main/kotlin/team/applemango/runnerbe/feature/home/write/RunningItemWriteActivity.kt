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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.home.write.component.RunningItemWrite
import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@AndroidEntryPoint
class RunningItemWriteActivity : ComponentActivity() {

    private val vm: RunningItemWriteViewModel by viewModels()

    @OptIn(LocalActivityUsageApi::class) // LocalActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowInsetsUsage()
        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                LaunchedEffect(Unit) {
                    vm.exceptionFlow
                        .defaultCatch(action = ::basicExceptionHandler)
                        .collectWithLifecycle(
                            lifecycleOwner = this@RunningItemWriteActivity,
                            action = { exception ->
                                basicExceptionHandler(exception)
                            }
                        )
                    vm.observe(
                        lifecycleOwner = this@RunningItemWriteActivity,
                        state = ::handleState,
                    )
                }

                RunningItemWrite(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.Background.Brush)
                        .systemBarsPadding()
                )
            }
        }
    }

    private fun handleState(state: RunningItemWriteState) {
        if (state == RunningItemWriteState.Done) {
            finishWithAnimation()
        }
    }
}
