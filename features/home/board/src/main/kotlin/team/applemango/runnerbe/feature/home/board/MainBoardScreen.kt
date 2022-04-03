/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardFragment.kt] created by Ji Sungbin on 22. 3. 15. 오후 6:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@OptIn(LocalActivityUsageApi::class) // LocalActivity usage
@Composable
fun MainBoard(
    vm: MainBoardViewModel = activityViewModel(),
    isBookmarkPage: Boolean,
) {
    val activity = LocalActivity.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var nonRegisterUserPopupVisible by remember { mutableStateOf(false) }
    var runningItemsState by remember { mutableStateOf<RunningItemsState>(RunningItemsState.Loading) }

    LaunchedEffect(Unit) {
        snapshotFlow { nonRegisterUserPopupVisible }
        /*.collectWithLifecycle(viewLifecycleOwner) { isVisible ->
            BottomSheetStateListenerHolder.bottomSheetStateListener?.onBottomSheetStateChanged(
                state = when (isVisible) {
                    true -> BottomSheetState.Expanding
                    else -> BottomSheetState.Hidden
                }
            )
        }*/

        vm.exceptionFlow
            .defaultCatch { exception ->
                activity.basicExceptionHandler(exception)
            }.collectWithLifecycle(lifecycleOwner = lifecycleOwner) { exception ->
                activity.basicExceptionHandler(exception)
            }

        // TODO: data prefetch
        // https://github.com/runner-be/RunnerBe-Android/issues/146
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
            lifecycleOwner = lifecycleOwner,
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

    Box(modifier = Modifier.fillMaxSize()) {
        MainBoardComposable(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = GradientAsset.Background.Brush),
            runningItems = (runningItemsState as? RunningItemsState.Loaded)?.items
                ?: emptyList(),
            isLoading = runningItemsState == RunningItemsState.Loading,
            isBookmarkPage = isBookmarkPage,
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
                    activity.changeActivityWithAnimation<DFMOnboardActivityAlias>()
                },
                onNegativeButtonClick = {
                    nonRegisterUserPopupVisible = false
                }
            )
        }
    }
}
