/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AgePicker.kt] created by Ji Sungbin on 22. 2. 9. 오후 10:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.github.jisungbin.logeukes.logeukes
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontTypeface
import team.applemango.runnerbe.shared.compose.util.presentationColorOf
import team.applemango.runnerbe.xml.numberpicker.OnValueChangeListener
import team.applemango.runnerbe.xml.numberpicker.WheelPicker
import java.util.Calendar

private val nowYear = Calendar.getInstance().get(Calendar.YEAR)

@Composable
internal fun AgePicker() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G5, thickness = 1.dp)
        AndroidView(modifier = Modifier.height(250.dp), factory = { context ->
            WheelPicker(context).apply {
                setSelectedTextColor(presentationColorOf(context, "primary"))
                setUnselectedTextColor(presentationColorOf(context, "G4"))
                setTypeface(FontTypeface.Roboto.getM(context))
                setWrapSelectorWheel(true)
                setWheelItemCount(5)
                setMinValue(nowYear - 80)
                setMaxValue(nowYear)
                setValue(nowYear / 2)
                setOnValueChangedListener(object : OnValueChangeListener {
                    override fun onValueChange(
                        picker: WheelPicker,
                        oldVal: String,
                        newVal: String,
                    ) {
                        logeukes { newVal }
                    }
                })
            }
        })
        Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G5, thickness = 1.dp)
    }
}
