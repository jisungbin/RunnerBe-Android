/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [CircleBorderText.kt] created by Ji Sungbin on 22. 3. 19. 오후 10:13
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.domain.extension.runIf

@Composable
internal fun CircleBorderText(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    innerPadding: Dp = 10.dp,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.White,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .border(width = borderWidth, color = borderColor)
            .runIf(onClick != null) {
                clickable(
                    enabled = enabled,
                    onClick = onClick!!
                )
            }
            .padding(innerPadding)
            .then(modifier),
        text = text,
        style = style,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}
