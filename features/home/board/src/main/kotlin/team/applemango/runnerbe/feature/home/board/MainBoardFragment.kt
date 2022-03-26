/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardFragment.kt] created by Ji Sungbin on 22. 3. 15. 오후 6:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.accompanist.placeholder.placeholder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.home.board.component.MainBoardComposable
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.setWindowInsets
import team.applemango.runnerbe.shared.android.extension.toast
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@AndroidEntryPoint
class MainBoardFragment : Fragment() {

    private val vm: MainBoardViewModel by activityViewModels()
    private val isEmptyState = MutableStateFlow(false)

    @OptIn(LocalActivityUsageApi::class) // LocalActivity usage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireActivity()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            var runningItemsState by remember { mutableStateOf<RunningItemsState>(RunningItemsState.Loading) }

            LaunchedEffect(Unit) {
                vm.observe(
                    lifecycleOwner = viewLifecycleOwner,
                    state = { state ->
                        var message = EmptyString
                        when (state) {
                            MainBoardState.NonRegisterUser -> {
                                message =
                                    getString(R.string.mainboard_toast_only_see_registered_user)
                            }
                            MainBoardState.RunningItemLoading -> {
                                runningItemsState = RunningItemsState.Loading
                            }
                            MainBoardState.RunningItemLoadFail -> {
                                runningItemsState = RunningItemsState.Loaded(emptyList())
                            }
                            is MainBoardState.RunningItemLoaded -> {
                                val items = state.items
                                runningItemsState = if (items.isNotEmpty()) {
                                    RunningItemsState.Loaded(state.items)
                                } else {
                                    RunningItemsState.Empty
                                }
                            }
                            MainBoardState.BookmarkToggleRequestFail -> {
                                EmptyString // TODO: 북마크 롤백 및 토스트 메시지
                            }
                        }
                        if (message.isNotEmpty()) {
                            toast(message)
                        }
                    }
                )
            }

            CompositionLocalProvider(LocalActivity provides requireActivity()) {
                MainBoardComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.BlackGradientBrush)
                        .statusBarsPadding()
                        .padding(16.dp)
                        .placeholder(
                            visible = runningItemsState == RunningItemsState.Loading,
                            color = ColorAsset.Primary,
                        ),
                    runningItems = (runningItemsState as? RunningItemsState.Loaded)?.items
                        ?: emptyList(),
                    isBookmarkPage = arguments?.getBoolean("bookmark") ?: false,
                    isEmptyState = runningItemsState == RunningItemsState.Empty
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setWindowInsets()
        vm.exceptionFlow
            .defaultCatch { exception ->
                requireActivity().basicExceptionHandler(exception)
            }.collectWithLifecycle(lifecycleOwner = viewLifecycleOwner) { exception ->
                requireActivity().basicExceptionHandler(exception)
            }
    }
}
