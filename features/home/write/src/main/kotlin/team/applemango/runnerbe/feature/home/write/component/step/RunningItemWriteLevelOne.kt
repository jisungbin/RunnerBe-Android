/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteLevelOne.kt] created by Ji Sungbin on 22. 3. 18. 오전 7:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component.step

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import java.util.Calendar
import java.util.Date
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.component.util.DateCache.plusDayAndCaching
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.shared.compose.component.RunnerbeDialog
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontTypeface
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.format
import team.applemango.runnerbe.shared.domain.extension.toCalendar
import team.applemango.runnerbe.shared.domain.unit.px
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPicker
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerColors
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerTextStyle

private val DefaultFieldShape = RoundedCornerShape(6.dp)

private val now = Date()
private fun Date.toDateString() = format("M/dd (E)")

@Composable
internal fun RunningItemWriteLevelOne(
    runningItemType: RunningItemType,
    fieldsAllInputStateChange: (state: Boolean) -> Unit,
) {
    var titleField by remember { mutableStateOf(TextFieldValue()) }
    val fieldsFillState = remember { mutableStateListOf(false, false, false) }

    var titleErrorVisible by remember { mutableStateOf(false) }

    fieldsAllInputStateChange(fieldsFillState.all { true })

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.runningitemwrite_label_title),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        TextField(
            value = titleField,
            shape = DefaultFieldShape,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = ColorAsset.G5_5),
            onValueChange = { newTitleValue ->
                if (newTitleValue.text.isNotEmpty()) {
                    fieldsFillState[0] = true
                }
                if (newTitleValue.text.length <= 30) {
                    titleField = newTitleValue
                    titleErrorVisible = false
                } else {
                    titleErrorVisible = true
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.runningitemwrite_placeholder_title),
                    style = Typography.Body14R.copy(color = ColorAsset.G3_5)
                )
            },
            textStyle = Typography.Body14R.copy(color = ColorAsset.G1)
        )
        AnimatedVisibility(
            modifier = Modifier.padding(top = 8.dp),
            visible = titleErrorVisible
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_error_title_length),
                style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            )
        }
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = ColorAsset.G6)
        Text(
            text = stringResource(R.string.runningitemwrite_label_date),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                }
                .padding(horizontal = 32.dp)
        ) {
            val (calendarIcon, dateString, arrowIcon) = createRefs()
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(calendarIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_schedule_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .constrainAs(dateString) {
                        start.linkTo(calendarIcon.end, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(DefaultFieldShape)
                    .background(color = ColorAsset.G5_5),
                text = "${now.toDateString()} ${runningItemType.toDefaultDateTime()}",
                style = Typography.Body14R.copy(color = ColorAsset.G1)
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(arrowIcon) {
                        start.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_chevron_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
private fun RunningDatePickerDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    runningDate: RunningDate,
    onRunningDateChange: (runningDate: RunningDate) -> Unit,
) {
    val context = LocalContext.current

    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        positiveButton = {
            textBuilder = {
                stringResource(R.string.runningitemwrite_dialog_button_decision)
            }
            onClick = {
                onDismissRequest()
            }
        },
        content = {
            Row(
                modifier = Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DateStringPicker { dateString ->
                    onRunningDateChange(runningDate.copy(dateString = dateString))
                }
                TimeTypePicker { timeType ->
                    onRunningDateChange(runningDate.copy(timeType = timeType))
                }
                Row {
                    Text(text = ":", style = Typography.Custom.SuperWheelPicker)
                    MinutePicker { minute ->
                        onRunningDateChange(runningDate.copy(minute = minute))
                    }
                }
            }
        }
    )
}

@Composable
private fun DateStringPicker(
    onDateStringSelectChange: (dateString: String) -> Unit,
) {
    val context = LocalContext.current
    SuperWheelPicker(
        colors = SuperWheelPickerColors(
            selectedTextColor = ColorAsset.Primary,
            unselectedTextColor = ColorAsset.G4
        ),
        textStyle = SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.medium(context),
            textSize = 26.px,
        ),
        wheelItemCount = 5,
        range = 0..6,
        value = 0,
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
    onTimeTypeSelectChange: (timeType: TimeType) -> Unit,
) {
    val context = LocalContext.current
    SuperWheelPicker(
        colors = SuperWheelPickerColors(
            selectedTextColor = ColorAsset.Primary,
            unselectedTextColor = ColorAsset.G4
        ),
        textStyle = SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.medium(context)
        ),
        range = 0..1,
        value = 0,
        wheelItemCount = 2,
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
    onHourSelectChange: (hour: Int) -> Unit,
) {
    val context = LocalContext.current
    SuperWheelPicker(
        colors = SuperWheelPickerColors(
            selectedTextColor = ColorAsset.Primary,
            unselectedTextColor = ColorAsset.G4
        ),
        textStyle = SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.medium(context)
        ),
        range = 0..11,
        value = now.toCalendar().get(Calendar.HOUR_OF_DAY),
        wheelItemCount = 2,
        onValueChange = { _, hour ->
            onHourSelectChange(hour)
        },
    )
}

@Composable
private fun MinutePicker(
    onMinuteSelectChange: (hour: Int) -> Unit,
) {
    val context = LocalContext.current
    SuperWheelPicker(
        colors = SuperWheelPickerColors(
            selectedTextColor = ColorAsset.Primary,
            unselectedTextColor = ColorAsset.G4
        ),
        textStyle = SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.medium(context)
        ),
        range = 0..12,
        value = now.toCalendar().get(Calendar.MINUTE),
        wheelItemCount = 5,
        onValueChange = { _, minute ->
            onMinuteSelectChange(minute)
        },
        onTextRender = { value ->
            val minute = value * 5
            if (minute == 60) {
                "0"
            } else {
                minute.toString()
            }
        }
    )
}
