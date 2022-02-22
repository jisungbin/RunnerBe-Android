/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [YearPicker.kt] created by Ji Sungbin on 22. 2. 9. 오후 10:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.datastore.preferences.core.edit
import java.util.Calendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.util.presentationColorOf
import team.applemango.runnerbe.shared.compose.extension.collectAsStateWithLifecycleRemember
import team.applemango.runnerbe.shared.compose.extension.collectWithLifecycleRememberOnLaunchedEffect
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontTypeface
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.xml.numberpicker.OnValueChangeListener
import team.applemango.runnerbe.xml.numberpicker.WheelPicker

private val nowYear = Calendar.getInstance().get(Calendar.YEAR)

@Composable
internal fun YearPicker(selectedYearChanged: (isAdult: Boolean) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val yearSelectFlow = remember { MutableStateFlow(nowYear) }
    val yearSelectState by yearSelectFlow.collectAsStateWithLifecycleRemember(nowYear)
    val wheelPicker = remember {
        WheelPicker(context) { year ->
            selectedYearChanged(nowYear - year > 19)
            coroutineScope.launch {
                yearSelectFlow.emit(year)
            }
        }
    }

    context.dataStore.data.collectWithLifecycleRememberOnLaunchedEffect { preferences ->
        preferences[DataStoreKey.Onboard.Year]?.let { restoreYear ->
            wheelPicker.setValue(restoreYear)
            yearSelectFlow.emit(restoreYear)
            selectedYearChanged(nowYear - restoreYear > 19)
        } ?: selectedYearChanged(false) // default year: ${nowYear} -> always isAdult: false
        cancel("onboard restore execute must be once.")
    }
    yearSelectFlow.collectWithLifecycleRememberOnLaunchedEffect(debounceTimeout = 300L) { year ->
        context.dataStore.edit { preferences ->
            preferences[DataStoreKey.Onboard.Year] = year
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G5, thickness = 1.dp)
        AndroidView(modifier = Modifier.height(250.dp), factory = {
            wheelPicker
        })
        Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G5, thickness = 1.dp)
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            visible = nowYear - yearSelectState <= 19
        ) {
            Text(
                text = StringAsset.Hint.AgeNotice,
                style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            )
        }
    }
}

private fun WheelPicker(
    context: Context,
    onNumberChangeListener: (number: Int) -> Unit,
) = WheelPicker(context).apply {
    setSelectedTextColor(presentationColorOf(context, "primary"))
    setUnselectedTextColor(presentationColorOf(context, "G4"))
    setTypeface(FontTypeface.Roboto.medium(context)) // can null
    setWrapSelectorWheel(true)
    setWheelItemCount(5)
    setMinValue(nowYear - 80)
    setMaxValue(nowYear)
    setValue(nowYear)
    setOnValueChangedListener(object : OnValueChangeListener {
        override fun onValueChange(
            picker: WheelPicker,
            oldVal: Int,
            newVal: Int,
        ) {
            onNumberChangeListener(newVal)
        }
    })
}
