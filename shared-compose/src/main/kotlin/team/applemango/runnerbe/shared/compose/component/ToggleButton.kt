/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ToggleButton.kt] created by Ji Sungbin on 22. 2. 10. 오전 5:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

@Composable
fun <T> ToggleButton(
    target: T,
    selectState: T?,
    targetStringBuilder: () -> String,
    onClick: () -> Unit,
) {
    @Composable
    fun backgroundColor(target: T) = animateColorAsState(
        if (target == selectState) ColorAsset.Primary else Color.Transparent
    ).value

    @Composable
    fun borderColor(target: T) = animateColorAsState(
        if (target == selectState) ColorAsset.Primary else ColorAsset.G4
    ).value

    @Composable
    fun textColor(target: T) = animateColorAsState(
        if (target == selectState) ColorAsset.G6 else ColorAsset.G3_5
    ).value

    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor(target)),
        border = BorderStroke(width = 1.dp, color = borderColor(target))
    ) {
        Text(
            text = targetStringBuilder(),
            style = Typography.Body14R.copy(color = textColor(target))
        )
    }
}
