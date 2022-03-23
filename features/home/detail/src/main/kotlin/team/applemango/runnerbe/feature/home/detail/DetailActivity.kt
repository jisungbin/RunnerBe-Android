/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DetailActivity.kt] created by Ji Sungbin on 22. 3. 22. 오후 10:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.feature.home.detail.component.BoardDetail
import team.applemango.runnerbe.feature.home.detail.mvi.DetailLoadState
import team.applemango.runnerbe.feature.home.detail.mvi.DetailSideEffect
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.android.extension.setWindowInsets
import team.applemango.runnerbe.shared.android.extension.toast
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.domain.constant.Intent
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    private val vm: DetailViewModel by viewModels()

    @OptIn(LocalActivityUsageApi::class) // LocalActivity usage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val runningItemId = intent.getIntExtra(Intent.Home.RunningItemId, -1)
        if (runningItemId == -1) {
            toast(getString(R.string.detail_toast_fail_load_item))
            finishWithAnimation()
            return
        }

        vm.exceptionFlow
            .defaultCatch(action = ::basicExceptionHandler)
            .collectWithLifecycle(lifecycleOwner = this) { exception ->
                basicExceptionHandler(exception)
            }

        setWindowInsets()
        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                var loadState by remember { mutableStateOf(DetailLoadState.Load) }
                var runningItemInformation by remember {
                    mutableStateOf<RunningItemInformation?>(null)
                }

                LaunchedEffect(Unit) {
                    vm.observe(
                        lifecycleOwner = this@DetailActivity,
                        state = { state ->
                            handleState(
                                state = state,
                                updateLoadState = { newState ->
                                    loadState = newState
                                }
                            )
                        },
                        sideEffect = { sideEffect ->
                            handleSideEffect(
                                sideEffect = sideEffect,
                                updateRunningItemInformation = { loadedRunningItemInformation ->
                                    runningItemInformation = loadedRunningItemInformation
                                }
                            )
                        }
                    )
                }

                Crossfade(
                    modifier = Modifier.fillMaxSize(), // Do NOT insert padding.
                    targetState = runningItemInformation != null
                ) { isLoaded ->
                    when (isLoaded) {
                        true -> {
                            BoardDetail(
                                modifier = Modifier.fillMaxSize(),
                                runningItemInformation = runningItemInformation!!
                            )
                        }
                        else -> {
                            // TODO: Loading Composable -> Skeleton UI
                            // Reference: https://google.github.io/accompanist/placeholder/
                        }
                    }
                }
            }
        }
    }

    private fun handleState(
        state: DetailLoadState,
        updateLoadState: (state: DetailLoadState) -> Unit,
    ) {
        when (state) {
            DetailLoadState.Load, DetailLoadState.Done -> {
                updateLoadState(state)
            }
            DetailLoadState.NotVerified -> {
                toast(getString(R.string.detail_toast_not_verified_account))
                finishWithAnimation()
            }
        }
    }

    private fun handleSideEffect(
        sideEffect: DetailSideEffect,
        updateRunningItemInformation: (item: RunningItemInformation) -> Unit,
    ) {
        when (sideEffect) {
            is DetailSideEffect.LoadDone -> {
                updateRunningItemInformation(sideEffect.item)
            }
        }
    }
}
