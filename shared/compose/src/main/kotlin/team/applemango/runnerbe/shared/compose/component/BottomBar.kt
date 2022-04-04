package team.applemango.runnerbe.shared.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable
import team.applemango.runnerbe.shared.domain.constant.EmptyString

@Immutable
data class BottomBarColors(
    val background: Color = Color.White,
    val indicatorBackground: Color = Color.LightGray,
    val primary: Color = Color.Blue,
    val activateIconTint: Color = Color.Unspecified,
    val inactivateIconTint: Color = Color.Unspecified,
)

@Immutable
data class BottomBarItem<T>(
    val id: T,
    val title: String = EmptyString,
    @DrawableRes val activateIcon: Int? = null,
    @DrawableRes val inactivateIcon: Int? = null,
)

@Composable
fun <T> BottomBar(
    modifier: Modifier = Modifier,
    colors: BottomBarColors = BottomBarColors(),
    fontFamily: FontFamily = FontFamily.Default,
    indicatorHeight: Dp = 1.dp,
    barHeight: Dp = 60.dp,
    titleTopPadding: Dp = 4.dp,
    items: List<BottomBarItem<T>>,
    selectedItemState: T,
    onItemSelected: (item: BottomBarItem<T>) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(barHeight)
            .background(color = colors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .noRippleClickable {
                        onItemSelected(item)
                    }
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(indicatorHeight)
                        .background(color = colors.indicatorBackground)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (item.activateIcon != null && item.inactivateIcon != null) {
                        Icon(
                            painter = painterResource(
                                when (selectedItemState == item.id) {
                                    true -> item.activateIcon
                                    else -> item.inactivateIcon
                                }
                            ),
                            contentDescription = null,
                            tint = when (selectedItemState == item.id) {
                                true -> colors.activateIconTint
                                else -> colors.inactivateIconTint
                            }
                        )
                    }
                    if (item.title.isNotBlank()) {
                        Text(
                            text = item.title,
                            color = colors.primary,
                            modifier = Modifier.padding(top = titleTopPadding),
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }
    }
}
