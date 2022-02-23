/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [IconBottomBar.kt] created by Ji Sungbin on 22. 2. 23. 오후 8:51
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.component

import androidx.annotation.DrawableRes
import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable

private const val DefaultBottomBarHeight = 56

internal data class StateIcon<T>(
    val id: T,
    @DrawableRes val activate: Int,
    @DrawableRes val inactivate: Int,
)

@Composable
internal fun <T> IconBottomBar(
    modifier: Modifier = Modifier,
    @Size(min = 1) stateIcons: List<StateIcon<T>>,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    activateIconTint: Color = contentColorFor(backgroundColor),
    height: Dp = DefaultBottomBarHeight.dp,
    onIconClick: (iconId: T) -> Unit,
) {
    require(stateIcons.isNotEmpty()) { "stateIcons size must be not zero." }
    var selectedIconState by remember { mutableStateOf(stateIcons.first().id) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        stateIcons.forEach { stateIcon ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .noRippleClickable {
                        onIconClick(stateIcon.id)
                        selectedIconState = stateIcon.id
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (selectedIconState == stateIcon.id) {
                            true -> stateIcon.activate
                            else -> stateIcon.inactivate
                        }
                    ),
                    contentDescription = null,
                    tint = activateIconTint
                )
            }
        }
    }
}
