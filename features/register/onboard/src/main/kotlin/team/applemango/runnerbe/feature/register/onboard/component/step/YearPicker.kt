/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [YearPicker.kt] created by Ji Sungbin on 22. 2. 9. 오후 10:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component.step

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.flowWithLifecycle
import java.util.Calendar
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerColors
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerTextStyle
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.domain.util.flowExceptionMessage
import team.applemango.runnerbe.shared.extension.dataStore
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPicker

private val nowYear = Calendar.getInstance().get(Calendar.YEAR)

@OptIn(FlowPreview::class)
@Composable
internal fun YearPicker(
    vm: OnboardViewModel,
    selectedYearChanged: (isAdult: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val yearSelectFlow = remember { MutableStateFlow(nowYear) }
    val yearSelectFlowWithLifecycle = remember(yearSelectFlow, lifecycleOwner) {
        yearSelectFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
    }
    val yearSelectStateWithLifecycle by yearSelectFlowWithLifecycle.collectAsState(nowYear)
    var nowYearState by remember { mutableStateOf(nowYear) }

    LaunchedEffect(Unit) {
        context
            .dataStore
            .data
            .cancellable()
            .catch { exception ->
                vm.emitException(
                    Exception(
                        flowExceptionMessage(
                            flowName = "YearPicker DataStore",
                            exception = exception
                        )
                    )
                )
            }
            .collect { preferences ->
                preferences[DataStoreKey.Onboard.Year]?.let { restoreYear ->
                    nowYearState = restoreYear
                    yearSelectFlow.emit(restoreYear)
                    selectedYearChanged(nowYear - restoreYear > 19)
                } ?: selectedYearChanged(false) // default year: ${nowYear} -> always isAdult: false
                cancel("onboard restore execute must be once.")
            }
    }

    LaunchedEffect(Unit) {
        yearSelectFlowWithLifecycle
            .debounce(300L)
            .collect { year ->
                context.dataStore.edit { preferences ->
                    preferences[DataStoreKey.Onboard.Year] = year
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G5, thickness = 1.dp)
        SuperWheelPicker(
            modifier = Modifier.height(250.dp),
            colors = RunnerbeSuperWheelPickerColors,
            textStyle = RunnerbeSuperWheelPickerTextStyle,
            range = nowYear - 80..nowYear,
            value = nowYearState,
            onValueChange = { _, newYear ->
                selectedYearChanged(nowYear - newYear > 19)
                coroutineScope.launch {
                    yearSelectFlow.emit(newYear)
                }
            }
        )
        Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G5, thickness = 1.dp)
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            visible = nowYear - yearSelectStateWithLifecycle <= 19
        ) {
            Text(
                text = StringAsset.Hint.AgeNotice,
                style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            )
        }
    }
}
