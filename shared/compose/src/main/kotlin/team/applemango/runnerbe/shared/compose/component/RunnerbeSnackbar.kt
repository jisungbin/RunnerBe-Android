package team.applemango.runnerbe.shared.compose.component

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import kotlin.math.roundToInt

@Composable
fun RunnerbeSnackbar(
    visible: Boolean,
    autoClose: Long? = 3000,
    text: String,
    textStyle: TextStyle = Typography.Body14R,
    backgroundColor: Color = ColorAsset.G4,
    bottomOffset: Float = 30f,
    horizontalPadding: Dp = 16.dp
) {
    var visibleState by remember { mutableStateOf(visible) }
    val offset by animateOffsetAsState(
        when (visibleState) {
            true -> Offset(0f, bottomOffset)
            else -> Offset(0f, -10f) // hide
        }
    )
    LaunchedEffect(Unit) {
        autoClose?.let {
            delay(autoClose)
            visibleState = false
        }
    }
    Row(
        modifier = Modifier
            .offset { offset.toIntOffset() }
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .blur(10.dp)
            .padding(horizontal = horizontalPadding)
    ) {
        Text(style = textStyle, text = text)
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())
