/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [PickerDialogs.kt] created by Ji Sungbin on 22. 3. 19. 오전 1:05
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.model.RunningTime
import team.applemango.runnerbe.feature.home.write.util.DateCache.plusDayAndCaching
import team.applemango.runnerbe.shared.compose.component.RunnerbeDialog
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerDefaults
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.toRunningDateString
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPicker
import java.util.Date

private val now = Date()

@Composable
internal fun RunningDatePickerDialog(
    visible: Boolean,
    startDateIndex: Int,
    startTimeType: TimeType,
    startHour: Int,
    startMinute: Int,
    onDismissRequest: () -> Unit,
    onRunningDateChange: (field: RunningDate.Companion.Field) -> Unit,
) {
    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        positiveButton = {
            textBuilder = {
                stringResource(R.string.runningitemwrite_dialog_button_decision)
            }
            onClick = onDismissRequest
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DateStringPicker(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp),
                    startDateIndex = startDateIndex
                ) { dateString ->
                    onRunningDateChange(RunningDate.Companion.Field.Date(dateString))
                }
                TimeTypePicker(
                    modifier = Modifier
                        .height(60.dp)
                        .width(40.dp),
                    startTimeType = startTimeType
                ) { timeType ->
                    onRunningDateChange(RunningDate.Companion.Field.TimeType(timeType))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HourPicker(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                        startHour = startHour,
                        hourRange = 1..12
                    ) { hour ->
                        onRunningDateChange(RunningDate.Companion.Field.Hour(hour))
                    }
                    Text(
                        text = ":",
                        style = Typography.Custom.SuperWheelPickerBold
                    )
                    MinutePicker(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                        startMinute = startMinute
                    ) { minute ->
                        onRunningDateChange(RunningDate.Companion.Field.Minute(minute))
                    }
                }
            }
        }
    )
}

@Composable
internal fun RunningTimePickerDialog(
    visible: Boolean,
    startHour: Int,
    startMinute: Int,
    onDismissRequest: () -> Unit,
    runningTime: RunningTime,
    onRunningTimeChange: (time: RunningTime) -> Unit,
) {
    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        positiveButton = {
            textBuilder = {
                stringResource(R.string.runningitemwrite_dialog_button_decision)
            }
            onClick = onDismissRequest
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HourPicker(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                        startHour = startHour,
                        hourRange = 0..5
                    ) { hour ->
                        onRunningTimeChange(runningTime.copy(hour = hour))
                    }
                    Text(
                        text = stringResource(R.string.runningitemwrite_dialog_picker_hour),
                        style = Typography.Custom.SuperWheelPickerRegular
                    )
                }
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MinutePicker(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                        startMinute = startMinute
                    ) { minute ->
                        onRunningTimeChange(runningTime.copy(minute = minute))
                    }
                    Text(
                        text = stringResource(R.string.runningitemwrite_dialog_picker_minute),
                        style = Typography.Custom.SuperWheelPickerRegular
                    )
                }
            }
        }
    )
}

@Composable
private fun DateStringPicker(
    modifier: Modifier,
    startDateIndex: Int,
    onDateStringSelectChange: (dateString: String) -> Unit,
) {
    SuperWheelPicker(
        modifier = modifier,
        colors = RunnerbeSuperWheelPickerDefaults.colors(),
        textStyle = RunnerbeSuperWheelPickerDefaults.textStyle(),
        wheelItemCount = 5,
        range = 0..7,
        value = startDateIndex,
        onValueChange = { _, dayUpper ->
            val newDateString = now.plusDayAndCaching(dayUpper).toRunningDateString()
            onDateStringSelectChange(newDateString)
        },
        onTextRender = { dayUpper ->
            now.plusDayAndCaching(dayUpper).toRunningDateString()
        }
    )
}

@Composable
private fun TimeTypePicker(
    modifier: Modifier,
    startTimeType: TimeType,
    onTimeTypeSelectChange: (timeType: TimeType) -> Unit,
) {
    SuperWheelPicker(
        modifier = modifier,
        colors = RunnerbeSuperWheelPickerDefaults.colors(),
        textStyle = RunnerbeSuperWheelPickerDefaults.textStyle(),
        wheelItemCount = 2,
        range = 0..1,
        value = startTimeType.ordinal,
        onValueChange = { _, timeTypeIndex ->
            onTimeTypeSelectChange(TimeType.values()[timeTypeIndex])
        },
        onTextRender = { value ->
            TimeType.values()[value].string
        }
    )
}

@Composable
private fun HourPicker(
    modifier: Modifier,
    startHour: Int,
    hourRange: kotlin.ranges.IntRange,
    onHourSelectChange: (hour: Int) -> Unit,
) {
    SuperWheelPicker(
        modifier = modifier,
        colors = RunnerbeSuperWheelPickerDefaults.colors(),
        textStyle = RunnerbeSuperWheelPickerDefaults.textStyle(),
        wheelItemCount = 5,
        range = hourRange,
        value = startHour,
        onValueChange = { _, hour ->
            onHourSelectChange(hour)
        },
    )
}

@Composable
private fun MinutePicker(
    modifier: Modifier,
    @IntRange(from = 0, to = 60) startMinute: Int,
    onMinuteSelectChange: (minute: Int) -> Unit,
) {
    SuperWheelPicker(
        modifier = modifier,
        colors = RunnerbeSuperWheelPickerDefaults.colors(),
        textStyle = RunnerbeSuperWheelPickerDefaults.textStyle(),
        wheelItemCount = 5,
        range = 0..12,
        value = startMinute / 5,
        onValueChange = { _, value ->
            val minute = value * 5
            onMinuteSelectChange(minute)
        },
        onTextRender = { value ->
            val minute = value * 5
            if (minute == 60) {
                "00"
            } else {
                String.format("%02d", minute)
            }
        }
    )
}
