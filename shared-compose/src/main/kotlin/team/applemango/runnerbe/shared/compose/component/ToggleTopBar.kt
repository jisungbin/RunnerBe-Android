/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ToggleTopBar.kt] created by Ji Sungbin on 22. 2. 23. 오후 10:33
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.annotation.Size
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable

private const val DefaultToggleTopBarRadius = 34
private const val DefaultToggleTopBarHeight = 36

data class TopBarItem<T>(
    val id: T,
    val text: String,
)

@Composable
fun <T> ToggleTopBar(
    modifier: Modifier = Modifier,
    baseBackgroundColor: Color = MaterialTheme.colors.primarySurface,
    activateBackgroundColor: Color,
    inactivateBackgroundColor: Color = baseBackgroundColor,
    activateTextColor: Color = contentColorFor(activateBackgroundColor),
    inactivateTextColor: Color = contentColorFor(inactivateBackgroundColor),
    textStyle: TextStyle = LocalTextStyle.current,
    height: Dp = DefaultToggleTopBarHeight.dp,
    radius: Dp = DefaultToggleTopBarRadius.dp,
    @Size(min = 1) topBarItems: List<TopBarItem<T>>,
    onItemClick: (itemId: T) -> Unit,
) {
    require(topBarItems.isNotEmpty()) { "topBarItems size must be not zero." }
    var selectedItemState by remember { mutableStateOf(topBarItems.first().id) }

    @Composable
    fun backgroundAnimateColor(itemId: T) = animateColorAsState(
        when (selectedItemState == itemId) {
            true -> activateBackgroundColor
            else -> inactivateBackgroundColor
        }
    ).value

    @Composable
    fun textAnimateColor(itemId: T) = animateColorAsState(
        when (selectedItemState == itemId) {
            true -> activateTextColor
            else -> inactivateTextColor
        }
    ).value

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(radius))
            .background(color = baseBackgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        topBarItems.forEach { item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(radius))
                    .background(color = backgroundAnimateColor(item.id))
                    .noRippleClickable {
                        onItemClick(item.id)
                        selectedItemState = item.id
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.text, style = textStyle.copy(color = textAnimateColor(item.id)))
            }
        }
    }
}
