/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardComposable.kt] created by Ji Sungbin on 22. 2. 23. 오후 11:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.runningitem.common.RunningItemSort
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.feature.home.board.BottomSheetStateListenerHolder
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.shared.android.constant.BottomNavigationBarHeight
import team.applemango.runnerbe.shared.compose.component.FadingEdgeLazyColumn
import team.applemango.runnerbe.shared.compose.component.Gradient
import team.applemango.runnerbe.shared.compose.component.RunningItemTypeToggleBar
import team.applemango.runnerbe.shared.compose.default.RunnerbeCheckBoxDefaults
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState

@OptIn(ExperimentalMaterialApi::class) // ModalBottomSheetLayout
@Composable
internal fun MainBoardComposable(
    modifier: Modifier = Modifier,
    runningItems: List<RunningItem>,
    isLoading: Boolean,
    isBookmarkPage: Boolean,
    isEmptyState: Boolean,
) {
    val coroutineScope = rememberCoroutineScope()

    val appNameText = stringResource(R.string.mainboard_title_app_name)
    val bookmarkText = stringResource(R.string.mainboard_title_bookmark_list)
    val titleText = remember(isBookmarkPage) {
        when (isBookmarkPage) {
            true -> bookmarkText
            else -> appNameText
        }
    }
    val titleTextStyle = remember(isBookmarkPage) {
        when (isBookmarkPage) {
            true -> Typography.Body16R.copy(color = ColorAsset.G3)
            else -> Typography.Custom.MainBoardTitle
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    var sortState by remember { mutableStateOf(RunningItemSort.Nearby) }
    var includeFinishState by remember { mutableStateOf(false) }
    var selectedRunningItemTypeState by remember { mutableStateOf(RunningItemType.Before) }
    val runningItemsState by remember(runningItems) {
        derivedStateOf {
            runningItems.apply {
                filter { item ->
                    item.runningType == selectedRunningItemTypeState &&
                        item.bookmarked == isBookmarkPage
                }
                sortedBy { item ->
                    when (sortState) {
                        RunningItemSort.Nearby -> item.distance.toLong()
                        RunningItemSort.Newest -> item.createdAt.time
                    }
                }
            }
        }
    }

    // FIXME: why not working on LaunchedEffect??
    LaunchedEffect(Unit) {
        logeukes { bottomSheetState.currentValue }
    }

    bottomSheetState.isAnimationRunning
    BottomSheetStateListenerHolder.bottomSheetStateListener?.onBottomSheetStateChanged(
        isExpended = bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    )?.also {
        logeukes { "Registered: ${bottomSheetState.currentValue == ModalBottomSheetValue.Expanded}" }
    }

    ModalBottomSheetLayout(
        modifier = modifier.zIndex(19f),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp
        ),
        sheetBackgroundColor = ColorAsset.G6,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .navigationBarsPadding()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            top = 24.dp,
                            bottom = 12.dp
                        )
                        .fillMaxWidth()
                ) {
                    items(items = RunningItemSort.values()) { sortItem ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = animatedColorState(
                                        target = sortState,
                                        selectState = sortItem,
                                        defaultColor = ColorAsset.G6,
                                        selectedColor = ColorAsset.G5_5
                                    )
                                )
                                .clickable {
                                    sortState = sortItem
                                }
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 32.dp
                                ),
                            text = sortItem.string,
                            style = Typography.Body16R.copy(
                                color = animatedColorState(
                                    target = sortState,
                                    selectState = sortItem,
                                    defaultColor = ColorAsset.G3,
                                    selectedColor = ColorAsset.Primary
                                )
                            )
                        )
                    }
                }
            }
        },
        content = {
            Box( // content + fab
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp) // without bottom: because LazyColumn
                    .navigationBarsPadding()
                    .padding(bottom = BottomNavigationBarHeight.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(modifier = Modifier.fillMaxSize()) { // content
                    Box( // ToolBar
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = titleText,
                            style = titleTextStyle
                        )
                        if (!isBookmarkPage) { // 타이틀 오른쪽 검색, 알림 아이템들
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 16.dp,
                                    alignment = Alignment.End
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_outlined_search_24),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_outlined_bell_24),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                    RunningItemTypeToggleBar(
                        modifier = Modifier.padding(top = 4.dp),
                        onTabClick = { runningItemType ->
                            selectedRunningItemTypeState = runningItemType
                        }
                    )
                    if (!isBookmarkPage) { // ToggleTopBar 아래 마감 포함, 거리순, 필터 아이템들
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 16.dp,
                                alignment = Alignment.End
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ToggleTopBarSubItem(
                                onClick = {
                                    includeFinishState = !includeFinishState
                                },
                                text = stringResource(R.string.mainboard_filter_include_finish)
                            ) {
                                Checkbox(
                                    modifier = Modifier.padding(start = 2.dp),
                                    checked = includeFinishState,
                                    onCheckedChange = { includeFinishState = it },
                                    colors = RunnerbeCheckBoxDefaults.colors()
                                )
                            }
                            ToggleTopBarSubItem(
                                onClick = {
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                                text = stringResource(R.string.mainboard_sort_nearby)
                            ) {
                                Icon(
                                    modifier = Modifier.padding(start = 2.dp),
                                    painter = painterResource(R.drawable.ic_round_chevron_down),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                            Icon(
                                modifier = Modifier.clickable {
                                    // TODO: filter activity
                                },
                                painter = painterResource(R.drawable.ic_round_filter_24),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }
                    Crossfade( // LazyColumn or Empty Screen
                        modifier = Modifier.fillMaxSize(),
                        targetState = isLoading
                    ) { loading ->
                        @Suppress("KotlinConstantConditions") // `!isEmptyState` is always true
                        /**
                         ```kotlin
                         fun main() {
                         when {
                         true -> println(1)
                         true -> println(2)
                         }
                         }
                         ```
                         result: 1
                         when 은 true 를 만나면 실행하고 break 한다.
                         */
                        when {
                            loading -> {
                                PlaceholderLazyColumn(enabled = isLoading)
                            }
                            isEmptyState -> {
                                RunningItemsEmpty()
                            }
                            !isEmptyState -> {
                                RunningItemsLazyColumn(
                                    runningItems = runningItemsState,
                                    requestToggleBookmarkState = { runningItem ->
                                        // TODO: requestToggleBookmarkState
                                    }
                                )
                            }
                        }
                    }
                }
                if (/*!isLoading*/ true) {
                    FloatingActionButton( // 글쓰기 FAB
                        modifier = Modifier.padding(bottom = 16.dp),
                        backgroundColor = Color.Transparent,
                        onClick = {
                            // TODO: write running item
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(brush = GradientAsset.Fab.Brush),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_round_add_24),
                                contentDescription = null,
                                tint = ColorAsset.Primary
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun ToggleTopBarSubItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.noRippleClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Typography.Body12R.copy(color = ColorAsset.G4)
        )
        content()
    }
}

@Composable
private fun PlaceholderLazyColumn(enabled: Boolean) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            top = 30.dp,
            bottom = 16.dp
        )
    ) {
        items(count = 10) {
            RunningItemScreenDummy(placeholderEnabled = enabled)
        }
    }
}

@Composable
private fun RunningItemsEmpty() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.mainboard_running_item_empty),
            style = Typography.Title18R.copy(color = ColorAsset.G4)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class) // Modifier.animateItemPlacement()
@Composable
private fun RunningItemsLazyColumn(
    runningItems: List<RunningItem>,
    requestToggleBookmarkState: (runningItem: RunningItem) -> Unit,
) {
    FadingEdgeLazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            top = 30.dp,
            bottom = 16.dp
        ),
        gradients = setOf(
            Gradient.Top(color = GradientAsset.Background.TopHalf)
        )
    ) {
        items(
            items = runningItems,
            key = { it.itemId }
        ) { runningItem ->
            RunningItemScreen(
                modifier = Modifier.animateItemPlacement(),
                item = runningItem,
                requestToggleBookmarkState = {
                    requestToggleBookmarkState(runningItem)
                }
            )
        }
    }
}
