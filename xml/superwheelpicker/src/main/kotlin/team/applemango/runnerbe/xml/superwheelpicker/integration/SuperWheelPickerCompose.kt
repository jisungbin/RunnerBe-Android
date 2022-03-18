/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SuperWheelPickerCompose.kt] created by Ji Sungbin on 22. 3. 18. 오전 8:53
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.xml.superwheelpicker.integration

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import team.applemango.runnerbe.xml.superwheelpicker.OnTextRenderListener
import team.applemango.runnerbe.xml.superwheelpicker.OnValueChangeListener

private typealias SuperWheelPickerXml = team.applemango.runnerbe.xml.superwheelpicker.SuperWheelPicker

@Immutable
data class SuperWheelPickerColors(
    val selectedTextColor: Color = Color.Black,
    val unselectedTextColor: Color = Color.LightGray,
)

@Immutable
data class SuperWheelPickerTextStyle(
    val typeface: Typeface? = Typeface.DEFAULT,
    val textSize: Float = SuperWheelPickerXml.DEFAULT_TEXT_SIZE,
)

@Composable
fun SuperWheelPicker(
    modifier: Modifier = Modifier,
    colors: SuperWheelPickerColors = SuperWheelPickerColors(),
    textStyle: SuperWheelPickerTextStyle = SuperWheelPickerTextStyle(),
    wrapSelectorWheel: Boolean = true,
    wheelItemCount: Int = SuperWheelPickerXml.DEFAULT_ITEM_COUNT,
    range: IntRange,
    value: Int = range.last / 2,
    onValueChange: OnValueChangeListener? = null,
    onTextRender: OnTextRenderListener? = null,
) = AndroidView(
    modifier = modifier,
    factory = { context ->
        SuperWheelPickerXml(context).apply {
            setSelectedTextColor(colors.selectedTextColor.toArgb())
            setUnselectedTextColor(colors.unselectedTextColor.toArgb())
            setTypeface(textStyle.typeface) // nullable
            setTextSize(textStyle.textSize)
            setWrapSelectorWheel(wrapSelectorWheel)
            setWheelItemCount(wheelItemCount)
            setRange(range)
            setValue(value)
            if (onValueChange != null) {
                setOnValueChangedListener(onValueChange)
            }
            if (onTextRender != null) {
                setTextRenderListener(onTextRender)
            }
        }
    }
)
