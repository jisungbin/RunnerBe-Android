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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import java.util.Date
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.model.RunningTime
import team.applemango.runnerbe.feature.home.write.model.toDateString
import team.applemango.runnerbe.feature.home.write.util.DateCache.plusDayAndCaching
import team.applemango.runnerbe.shared.compose.component.RunnerbeDialog
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerColors
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerTextStyle
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.unit.em
import team.applemango.runnerbe.shared.domain.unit.px
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPicker

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
                modifier = Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DateStringPicker(startDateIndex = startDateIndex) { dateString ->
                    onRunningDateChange(RunningDate.Companion.Field.Date(dateString))
                }
                TimeTypePicker(startTimeType = startTimeType) { timeType ->
                    onRunningDateChange(RunningDate.Companion.Field.TimeType(timeType))
                }
                Row {
                    HourPicker(
                        startHour = startHour,
                        hourRange = 1..12
                    ) { hour ->
                        onRunningDateChange(RunningDate.Companion.Field.Hour(hour))
                    }
                    Text(text = ":", style = Typography.Custom.SuperWheelPickerBold)
                    MinutePicker(startMinute = startMinute) { minute ->
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
                modifier = Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    HourPicker(
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
                Row {
                    MinutePicker(startMinute = startMinute) { minute ->
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
    startDateIndex: Int,
    onDateStringSelectChange: (dateString: String) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 5,
        range = 0..7,
        value = startDateIndex,
        onValueChange = { _, dayUpper ->
            val newDateString = now.plusDayAndCaching(dayUpper).toDateString()
            onDateStringSelectChange(newDateString)
        },
        onTextRender = { value ->
            now.plusDayAndCaching(value).toDateString()
        }
    )
}

@Composable
private fun TimeTypePicker(
    startTimeType: TimeType,
    onTimeTypeSelectChange: (timeType: TimeType) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 2,
        range = 0..1,
        value = TimeType.values().indexOf(startTimeType),
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
    startHour: Int,
    hourRange: kotlin.ranges.IntRange,
    onHourSelectChange: (hour: Int) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
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
    @IntRange(from = 0, to = 60) startMinute: Int,
    onMinuteSelectChange: (hour: Int) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 5,
        range = 0..12,
        value = startMinute / 5,
        onValueChange = { _, minute ->
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
