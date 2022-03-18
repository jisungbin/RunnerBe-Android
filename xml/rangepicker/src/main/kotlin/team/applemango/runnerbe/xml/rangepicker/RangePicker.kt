/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RangePicker.kt] created by Ji Sungbin on 22. 3. 19. 오전 8:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.xml.rangepicker

import android.content.Context
import android.content.res.ColorStateList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.slider.RangeSlider
import team.applemango.runnerbe.shared.domain.unit.Dp
import team.applemango.runnerbe.shared.domain.unit.dp

private typealias FloatRange = ClosedFloatingPointRange<Float>

@Immutable
data class Track(
    val colorActive: Color = Color.Black,
    val colorInactive: Color = Color.Gray,
    val height: Dp = 4.dp,
)

@Immutable
data class Thumb(
    val color: Color = Color.Black,
    val radius: Dp = 10.dp,
    val elevation: Dp = 1.dp,
    val haloColor: Color = Color.Black.copy(alpha = 0.3f),
    val haloRadius: Dp = 24.dp,
)

@Immutable
data class Tick(
    val color: Color = Color.DarkGray,
)

@Composable
fun RangePicker(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
        }
    )
}

private fun RangeSlider(
    context: Context,
    range: FloatRange,
    value: FloatRange,
    step: Float,
    trackOption: Track = Track(),
    thumbOption: Thumb = Thumb(),
    tickOption: Tick = Tick(),
): RangeSlider {
    RangeSlider(context).apply {
        valueFrom = range.start
        valueTo = range.endInclusive
        values = listOf(value.start, value.endInclusive)
        stepSize = step
        trackActiveTintList = ColorStateList.valueOf(trackOption.colorActive.toArgb())
        trackInactiveTintList = ColorStateList.valueOf(trackOption.colorInactive.toArgb())
        trackHeight = trackOption.height.value.toInt()
        thumbTintList = ColorStateList.valueOf(thumbOption.color.toArgb())
        thumbRadius = thumbOption.radius.value.toInt()
        thumbElevation = thumbOption.elevation.value
        haloTintList = ColorStateList.valueOf(thumbOption.haloColor.toArgb())
        haloRadius = thumbOption.haloRadius.value.toInt()
        tickTintList = ColorStateList.valueOf(tickOption.color.toArgb())
        addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                TODO("Not yet implemented")
            }
        })
    }
}
