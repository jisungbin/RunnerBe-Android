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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.home.write.component.RunningItemWrite
import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.compose.component.RunnerbeDialog
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@AndroidEntryPoint
class RunningItemWriteActivity : ComponentActivity() {

    private val vm: RunningItemWriteViewModel by viewModels()

    @OptIn(
        LocalActivityUsageApi::class, // LocalActivity
        ExperimentalFoundationApi::class // LocalOverScrollConfiguration
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()
        setWindowInsetsUsage()
        vm.exceptionFlow
            .defaultCatch(action = ::basicExceptionHandler)
            .collectWithLifecycle(
                lifecycleOwner = this@RunningItemWriteActivity,
                action = { exception ->
                    basicExceptionHandler(exception)
                }
            )

        setContent {
            var restoreLastDataState by remember { mutableStateOf(false) }
            var restoreLastDataDialogVisibleState by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                val preference = applicationContext
                    .dataStore
                    .data
                    .defaultCatch(action = vm::emitException)
                    .first()

                if (
                    listOf(
                        preference[DataStoreKey.Write.Title],
                        preference[DataStoreKey.Write.Message],
                        preference[DataStoreKey.Write.Gender],
                        preference[DataStoreKey.Write.MaxPeopleCount],
                        preference[DataStoreKey.Write.AgeFilter],
                        preference[DataStoreKey.Write.ItemType],
                        preference[DataStoreKey.Write.RunningDate],
                        preference[DataStoreKey.Write.RunningTime],
                        preference[DataStoreKey.Write.Locate]
                    ).filterNot { it == null }.isNotEmpty()
                ) {
                    restoreLastDataDialogVisibleState = true
                }
            }

            RunnerbeDialog(
                visible = restoreLastDataDialogVisibleState,
                onDismissRequest = { restoreLastDataDialogVisibleState = false },
                content = stringResource(R.string.runningitemwrite_dialog_can_restore_last_data),
                positiveButton = {
                    textBuilder = {
                        stringResource(R.string.runningitemwrite_dialog_button_yes)
                    }
                    onClick = {
                        restoreLastDataState = true
                    }
                },
                negativeButton = {
                    textBuilder = {
                        stringResource(R.string.runningitemwrite_dialog_button_no)
                    }
                }
            )

            CompositionLocalProvider(
                LocalActivity provides this,
                LocalOverScrollConfiguration provides null
            ) {
                LaunchedEffect(Unit) {
                    vm.observe(
                        lifecycleOwner = this@RunningItemWriteActivity,
                        state = ::handleState,
                    )
                }

                RunningItemWrite(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.Background.Brush)
                        .systemBarsPadding(),
                    restoreLastData = restoreLastDataState
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
