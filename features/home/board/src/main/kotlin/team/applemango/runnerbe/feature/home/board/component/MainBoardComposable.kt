/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardComposable.kt] created by Ji Sungbin on 22. 2. 23. 오후 11:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.component

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.board.MainBoardViewModel
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.shared.compose.component.RunningItemTypeToggleBar
import team.applemango.runnerbe.shared.compose.default.RunnerbeCheckBoxDefaults
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

@OptIn(
    ExperimentalFoundationApi::class, // Modifier.animateItemPlacement()
    LocalActivityUsageApi::class // activityViewModel()
)
@Composable
internal fun MainBoardComposable(
    modifier: Modifier = Modifier,
    isBookmarkPage: Boolean = false,
    vm: MainBoardViewModel = activityViewModel(),
    isEmptyState: Boolean = false,
) {
    val runningItems by vm.runningItems.collectAsState()

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

    var includeFinishState by remember { mutableStateOf(false) }
    var selectedRunningItemTypeState by remember { mutableStateOf(RunningItemType.Before) }

    Column(modifier = modifier) {
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
                    onClick = { /*TODO*/ },
                    text = stringResource(R.string.mainboard_filter_include_finish)
                ) {
                    Checkbox(
                        modifier = Modifier.padding(start = 4.dp),
                        checked = includeFinishState,
                        onCheckedChange = { includeFinishState = it },
                        colors = RunnerbeCheckBoxDefaults.colors()
                    )
                }
                ToggleTopBarSubItem(
                    onClick = { /*TODO*/ },
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
                        // TODO
                    },
                    painter = painterResource(R.drawable.ic_round_filter_24),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
        if (!isEmptyState) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = runningItems.filter { item ->
                        item.runningType == selectedRunningItemTypeState &&
                            item.bookmarked == isBookmarkPage
                    },
                    key = { it.itemId }
                ) { item ->
                    RunningItem(
                        modifier = Modifier.animateItemPlacement(),
                        item = item,
                        bookmarkState = false,
                        requestToggleBookmarkState = {
                            // TODO
                        }
                    )
                }
            }
        } else {
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
    }
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
