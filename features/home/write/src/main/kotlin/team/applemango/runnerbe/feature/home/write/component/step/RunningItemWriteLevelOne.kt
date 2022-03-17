/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteLevelOne.kt] created by Ji Sungbin on 22. 3. 18. 오전 7:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component.step

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

@Composable
internal fun RunningItemWriteLevelOne(
    fieldsAllInputStateChange: (state: Boolean) -> Unit,
) {
    var titleField by remember { mutableStateOf(TextFieldValue()) }
    val fieldsFillState = remember { mutableStateListOf(false, false, false) }

    var titleErrorVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.runningitemwrite_label_title),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        TextField(
            value = titleField,
            onValueChange = { newTitleValue ->
                if (newTitleValue.text.isNotEmpty()) {
                    fieldsFillState[0] = true
                }
                if (newTitleValue.text.length <= 30) {
                    titleField = newTitleValue
                    titleErrorVisible = false
                } else {
                    titleErrorVisible = true
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.runningitemwrite_placeholder_title),
                    style = Typography.Body14R.copy(color = ColorAsset.G3_5)
                )
            },
            textStyle = Typography.Body14R.copy(color = ColorAsset.G1)
        )
        AnimatedVisibility(
            modifier = Modifier.padding(top = 8.dp),
            visible = titleErrorVisible
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_error_title_length),
                style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            )
        }
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = ColorAsset.G6)
        Text(
            text = stringResource(R.string.runningitemwrite_label_date),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
    }
}
