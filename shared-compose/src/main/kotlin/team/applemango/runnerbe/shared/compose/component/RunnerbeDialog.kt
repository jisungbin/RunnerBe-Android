/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeDialog.kt] created by Ji Sungbin on 22. 2. 23. 오전 2:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.dsl.RunnerbeDsl

@Immutable
data class DialogButton(
    var text: String = "",
    var textBuilder: @Composable () -> String = { "" },
    var onClick: () -> Unit = {},
)

@Composable
fun RunnerbeDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable ColumnScope.() -> Unit,
) {
    if (visible) {
        CustomAlertDialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = ColorAsset.G5)
            ) {
                content()
            }
        }
    }
}

@Composable
fun RunnerbeDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: String,
    @RunnerbeDsl positiveButton: DialogButton.() -> Unit,
    @RunnerbeDsl negativeButton: (DialogButton.() -> Unit)? = null,
) {
    val buttonShape = RoundedCornerShape(10.dp)
    val positiveButtonData = DialogButton().apply(positiveButton)

    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Text(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 12.dp)
                .padding(horizontal = 24.dp),
            text = content,
            style = Typography.Title18R.copy(color = ColorAsset.G1)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.End
            )
        ) {
            if (negativeButton != null) {
                val negativeButtonData = DialogButton().apply(negativeButton)
                Text(
                    modifier = Modifier
                        .clip(buttonShape)
                        .clickable(onClick = negativeButtonData.onClick)
                        .padding(12.dp),
                    text = negativeButtonData.text.ifEmpty { negativeButtonData.textBuilder() },
                    style = Typography.Body14M.copy(color = ColorAsset.Primary)
                )
            }
            Text(
                modifier = Modifier
                    .clip(buttonShape)
                    .clickable(onClick = positiveButtonData.onClick)
                    .padding(12.dp),
                text = positiveButtonData.text.ifEmpty { positiveButtonData.textBuilder() },
                style = Typography.Body14M.copy(color = ColorAsset.Primary)
            )
        }
    }
}

@Composable
fun RunnerbeDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable BoxScope.() -> Unit,
    @RunnerbeDsl positiveButton: DialogButton.() -> Unit,
    @RunnerbeDsl negativeButton: (DialogButton.() -> Unit)? = null,
) {
    val buttonShape = RoundedCornerShape(10.dp)
    val positiveButtonData = DialogButton().apply(positiveButton)

    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 12.dp)
                .padding(horizontal = 24.dp)
        ) {
            content()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.End
            )
        ) {
            if (negativeButton != null) {
                val negativeButtonData = DialogButton().apply(negativeButton)
                Text(
                    modifier = Modifier
                        .clip(buttonShape)
                        .clickable(onClick = negativeButtonData.onClick)
                        .padding(12.dp),
                    text = negativeButtonData.text.ifEmpty { negativeButtonData.textBuilder() },
                    style = Typography.Body14M.copy(color = ColorAsset.Primary)
                )
            }
            Text(
                modifier = Modifier
                    .clip(buttonShape)
                    .clickable(onClick = positiveButtonData.onClick)
                    .padding(12.dp),
                text = positiveButtonData.text.ifEmpty { positiveButtonData.textBuilder() },
                style = Typography.Body14M.copy(color = ColorAsset.Primary)
            )
        }
    }
}
