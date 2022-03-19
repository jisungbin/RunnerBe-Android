/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ToggleButton.kt] created by Ji Sungbin on 22. 2. 10. 오전 5:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState

@Immutable
data class ToggleButtonColors(
    val backgroundDefaultColor: Color = Color.Transparent,
    val backgroundSelectedColor: Color = Color.Black,
    val borderDefaultColor: Color = backgroundDefaultColor,
    val borderSelectedColor: Color = backgroundSelectedColor,
)

@Immutable
data class ToggleButtonTextStyle(
    val defaultColor: Color = Color.LightGray,
    val selectedColor: Color = Color.Black,
)

@Composable
fun <T> ToggleButton(
    modifier: Modifier = Modifier,
    target: T,
    selectState: T?,
    targetStringBuilder: (target: T) -> String,
    colors: ToggleButtonColors = ToggleButtonColors(),
    textStyle: ToggleButtonTextStyle = ToggleButtonTextStyle(),
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = animatedColorState(
                target = target,
                selectState = selectState,
                defaultColor = colors.backgroundDefaultColor,
                selectedColor = colors.backgroundSelectedColor
            )
        ),
        border = BorderStroke(
            width = 1.dp,
            color = animatedColorState(
                target = target,
                selectState = selectState,
                defaultColor = colors.borderDefaultColor,
                selectedColor = colors.borderSelectedColor
            )
        )
    ) {
        Text(
            text = targetStringBuilder(target),
            style = Typography.Body14R.copy(
                color = animatedColorState(
                    target = target,
                    selectState = selectState,
                    defaultColor = textStyle.defaultColor,
                    selectedColor = textStyle.selectedColor
                )
            )
        )
    }
}
