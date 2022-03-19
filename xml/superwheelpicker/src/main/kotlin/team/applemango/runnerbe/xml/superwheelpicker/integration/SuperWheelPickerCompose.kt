/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SuperWheelPickerCompose.kt] created by Ji Sungbin on 22. 3. 18. 오전 8:53
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.xml.superwheelpicker.integration

import android.content.Context
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import team.applemango.runnerbe.shared.domain.unit.Em
import team.applemango.runnerbe.shared.domain.unit.Px
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
    val textSize: Px = Px(SuperWheelPickerXml.DEFAULT_TEXT_SIZE),
    val letterSpacing: Em = Em(0f),
)

@Composable
fun SuperWheelPicker(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
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
        SuperWheelPicker(
            context = context,
            colors = colors,
            textStyle = textStyle,
            wrapSelectorWheel = wrapSelectorWheel,
            wheelItemCount = wheelItemCount,
            range = range,
            value = value,
            onValueChange = onValueChange,
            onTextRender = onTextRender
        )
    },
    update = { superWheelPicker ->
        superWheelPicker.isEnabled = enabled
    }
)

private fun SuperWheelPicker(
    context: Context,
    colors: SuperWheelPickerColors = SuperWheelPickerColors(),
    textStyle: SuperWheelPickerTextStyle = SuperWheelPickerTextStyle(),
    wrapSelectorWheel: Boolean = true,
    wheelItemCount: Int = SuperWheelPickerXml.DEFAULT_ITEM_COUNT,
    range: IntRange,
    value: Int = range.last / 2,
    onValueChange: OnValueChangeListener? = null,
    onTextRender: OnTextRenderListener? = null,
) = SuperWheelPickerXml(context).apply {
    setSelectedTextColor(colors.selectedTextColor.toArgb())
    setUnselectedTextColor(colors.unselectedTextColor.toArgb())
    setTypeface(textStyle.typeface) // nullable
    setTextSize(textStyle.textSize.value)
    setTextLetterSpacing(textStyle.letterSpacing.value)
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
