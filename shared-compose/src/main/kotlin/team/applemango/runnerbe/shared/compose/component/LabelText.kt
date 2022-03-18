/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LabelText.kt] created by Ji Sungbin on 22. 3. 19. 오전 7:37
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LabelText(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    iconTint: Color = Color.Unspecified,
    iconSize: Dp = 24.dp,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current,
    labelStartPadding: Dp = 4.dp,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTint
        )
        Text(
            modifier = Modifier.padding(start = labelStartPadding),
            text = label,
            style = textStyle
        )
    }
}
