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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.filter.KeywordFilter
import team.applemango.runnerbe.domain.runningitem.filter.RunningItemFilter
import team.applemango.runnerbe.feature.home.board.component.MainBoardComposable
import team.applemango.runnerbe.feature.home.board.component.NonRegisterUserPopup
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState
import team.applemango.runnerbe.feature.home.board.mvi.RunningItemsState
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.util.DFMOnboardActivityAlias
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@AndroidEntryPoint
class MainBoardFragment : Fragment() {

    private val vm: MainBoardViewModel by activityViewModels()

    @OptIn(LocalActivityUsageApi::class) // LocalActivity usage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireActivity()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            var nonRegisterUserPopupVisible by remember { mutableStateOf(true) }
            var runningItemsState by remember { mutableStateOf<RunningItemsState>(RunningItemsState.Loading) }

            LaunchedEffect(Unit) {
                snapshotFlow { nonRegisterUserPopupVisible }
                    .collectWithLifecycle(viewLifecycleOwner) { isVisible ->
                        BottomSheetStateListenerHolder.bottomSheetStateListener?.onBottomSheetStateChanged(
                            state = when (isVisible) {
                                true -> BottomSheetState.Expanding
                                else -> BottomSheetState.Hidden
                            }
                        )
                    }

                vm.loadRunningItems(
                    itemType = RunningItemType.Before,
                    includeEndItems = false,
                    itemFilter = RunningItemFilter.Distance,
                    distanceFilter = DistanceFilter.None,
                    genderFilter = Gender.All,
                    ageFilter = AgeFilter(min = null, max = null),
                    jobFilter = JobFilter.None,
                    locate = Me.locate.value,
                    keywordFilter = KeywordFilter.None
                )
                vm.observe(
                    lifecycleOwner = viewLifecycleOwner,
                    state = { state ->
                        when (state) {
                            MainBoardState.NonRegisterUser -> {
                                nonRegisterUserPopupVisible = true
                            }
                            MainBoardState.RunningItemLoading -> {
                                runningItemsState = RunningItemsState.Loading
                            }
                            MainBoardState.RunningItemLoadFail -> {
                                runningItemsState = RunningItemsState.LoadFail
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
                                // TODO: 북마크 롤백 및 토스트 메시지
                            }
                        }
                    }
                )
            }

            CompositionLocalProvider(LocalActivity provides requireActivity()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MainBoardComposable(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = GradientAsset.Background.Brush)
                            .statusBarsPadding(), // without navigationBar: because use BottomSheetView inner
                        runningItems = (runningItemsState as? RunningItemsState.Loaded)?.items
                            ?: emptyList(),
                        isLoading = runningItemsState == RunningItemsState.Loading,
                        isBookmarkPage = arguments?.getBoolean("bookmark") ?: false,
                        isEmptyState = runningItemsState == RunningItemsState.Empty
                    )

                    AnimatedVisibility(
                        modifier = Modifier.fillMaxSize(),
                        visible = nonRegisterUserPopupVisible,
                        enter = fadeIn(tween(durationMillis = 500)),
                        exit = fadeOut(tween(durationMillis = 500))
                    ) {
                        NonRegisterUserPopup(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Black.copy(alpha = 0.7f)),
                            onPositiveButtonClick = {
                                requireActivity().changeActivityWithAnimation<DFMOnboardActivityAlias>()
                            },
                            onNegativeButtonClick = {
                                nonRegisterUserPopupVisible = false
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.exceptionFlow
            .defaultCatch { exception ->
                requireActivity().basicExceptionHandler(exception)
            }.collectWithLifecycle(lifecycleOwner = viewLifecycleOwner) { exception ->
                requireActivity().basicExceptionHandler(exception)
            }
    }
}
