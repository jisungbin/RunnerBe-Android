/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoard.kt] created by Ji Sungbin on 22. 2. 23. 오후 11:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import team.applemango.runnerbe.domain.main.constant.RunningItemType
import team.applemango.runnerbe.shared.compose.component.ToggleTopBar
import team.applemango.runnerbe.shared.compose.component.ToggleTopBarItem
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

@Composable
fun MainBoard(
    modifier: Modifier = Modifier,
    isBookmark: Boolean = false,
) {
    val beforeText = stringResource(R.string.onboard_toggletopbaritem_before)
    val afterText = stringResource(R.string.onboard_toggletopbaritem_after)
    val offText = stringResource(R.string.onboard_toggletopbaritem_off)
    val appNameText = stringResource(R.string.mainboard_title_app_name)
    val bookmarkText = stringResource(R.string.mainboard_title_bookmark_list)
    var selectedRunningItemType by remember { mutableStateOf(RunningItemType.Before) }
    val toggleTabBarItems = remember {
        listOf(
            ToggleTopBarItem(id = RunningItemType.Before, text = beforeText),
            ToggleTopBarItem(id = RunningItemType.Before, text = afterText),
            ToggleTopBarItem(id = RunningItemType.Before, text = offText),
        )
    }
    val titleText = remember(isBookmark) {
        when (isBookmark) {
            true -> bookmarkText
            else -> appNameText
        }
    }
    val titleTextStyle = remember(isBookmark) {
        when (isBookmark) {
            true -> Typography.Body16R.copy(color = ColorAsset.G3)
            else -> TextStyle(
                fontFamily = FontAsset.Aggro,
                color = ColorAsset.PrimaryDark,
                fontSize = 16.sp,
                lineHeight = 31.sp,
                letterSpacing = (-0.4).sp
            )
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Box( // ToolBar
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = titleText, style = titleTextStyle)
            if (!isBookmark) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.End
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_search_24),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_round_bell_24),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
        ToggleTopBar(
            modifier = Modifier.padding(top = 4.dp),
            toggleTopBarItems = toggleTabBarItems,
            baseBackgroundColor = ColorAsset.G6,
            activateBackgroundColor = ColorAsset.Primary,
            activateTextColor = ColorAsset.G6,
            inactivateTextColor = ColorAsset.G4_5,
            activateTextStyle = Typography.Body14M,
            inactivateTextStyle = Typography.Body14R,
            onItemClick = { runningItemType ->
                selectedRunningItemType = runningItemType
            }
        )
        if (!isBookmark) {
        }
    }
}
