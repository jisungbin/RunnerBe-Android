/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BorderText.kt] created by Ji Sungbin on 22. 3. 19. 오후 10:13
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.domain.extension.runIf

@Immutable
data class BorderOption(
    val width: Dp = 1.dp,
    val color: Color = Color.White,
)

@Immutable
data class TextOption(
    val overflow: TextOverflow = TextOverflow.Clip,
    val softWrap: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
)

@Composable
fun RoundBorderText(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    innerPadding: PaddingValues = PaddingValues(10.dp),
    text: String,
    style: TextStyle = LocalTextStyle.current,
    borderOption: BorderOption = BorderOption(),
    textOption: TextOption = TextOption(),
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = borderOption.width,
                color = borderOption.color,
                shape = CircleShape
            )
            .runIf(onClick != null) {
                clickable(
                    enabled = enabled,
                    onClick = onClick!!
                )
            }
            .padding(innerPadding),
        text = text,
        style = style,
        overflow = textOption.overflow,
        softWrap = textOption.softWrap,
        maxLines = textOption.maxLines
    )
}

@Composable
fun CircleBorderText(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 40.dp,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    borderOption: BorderOption = BorderOption(),
    textOption: TextOption = TextOption(),
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(
                width = borderOption.width,
                color = borderOption.color,
                shape = CircleShape
            )
            .runIf(onClick != null) {
                clickable(
                    enabled = enabled,
                    onClick = onClick!!
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style.copy(textAlign = TextAlign.Center),
            overflow = textOption.overflow,
            softWrap = textOption.softWrap,
            maxLines = textOption.maxLines
        )
    }
}
