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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState

@Composable
fun <T> ToggleButton(
    modifier: Modifier = Modifier,
    target: T,
    selectState: T?,
    targetStringBuilder: () -> String,
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
                defaultColor = Color.Transparent,
                selectedColor = ColorAsset.Primary
            )
        ),
        border = BorderStroke(
            width = 1.dp,
            color = animatedColorState(
                target = target,
                selectState = selectState,
                defaultColor = ColorAsset.G4,
                selectedColor = ColorAsset.Primary
            )
        )
    ) {
        Text(
            text = targetStringBuilder(),
            style = Typography.Body14R.copy(
                color = animatedColorState(
                    target = target,
                    selectState = selectState,
                    defaultColor = ColorAsset.G3_5,
                    selectedColor = ColorAsset.G6
                )
            )
        )
    }
}
