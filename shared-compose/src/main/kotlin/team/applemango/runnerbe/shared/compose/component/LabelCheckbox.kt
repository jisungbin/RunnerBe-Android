/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LabelCheckBox.kt] created by Ji Sungbin on 22. 3. 19. 오전 7:00
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LabelCheckbox(
    modifier: Modifier = Modifier,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current,
    checkboxStartPadding: Dp = 4.dp,
    checkboxColors: CheckboxColors = CheckboxDefaults.colors(),
    checkboxState: Boolean,
    checkboxStateChange: ((checked: Boolean) -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = textStyle
        )
        Checkbox(
            modifier = Modifier.padding(start = checkboxStartPadding),
            checked = checkboxState,
            onCheckedChange = checkboxStateChange,
            colors = checkboxColors
        )
    }
}
