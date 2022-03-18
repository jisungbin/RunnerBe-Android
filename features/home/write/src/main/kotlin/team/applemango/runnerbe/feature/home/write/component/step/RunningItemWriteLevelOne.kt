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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.component.RunningDatePickerDialog
import team.applemango.runnerbe.feature.home.write.component.RunningTimePickerDialog
import team.applemango.runnerbe.feature.home.write.datastore.DataStore
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.model.RunningTime
import team.applemango.runnerbe.feature.home.write.util.DateCache
import team.applemango.runnerbe.feature.home.write.util.extension.toAddress
import team.applemango.runnerbe.feature.home.write.util.extension.toLatLng
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.extension.collectWithLifecycle

private const val DefaultMapCameraZoom = 7f
private val DefaultFieldShape = RoundedCornerShape(6.dp)

@Composable
internal fun RunningItemWriteLevelOne(
    modifier: Modifier = Modifier,
    runningItemType: RunningItemType,
    fieldsAllInputStateChange: (state: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var titleField by remember { mutableStateOf(TextFieldValue()) }
    var runningDateState by remember { mutableStateOf(RunningDate.getDefault(runningItemType)) }
    var runningTimeState by remember { mutableStateOf(RunningTime(hour = 0, minute = 20)) }
    val fieldsFillState = remember { mutableStateListOf(false, false, false) }

    var runningDatePickerDialogVisible by remember { mutableStateOf(false) }
    var runningTimePickerDialogVisible by remember { mutableStateOf(false) }
    var titleErrorVisible by remember { mutableStateOf(false) }

    val myLocate = remember { DataStore.lastLocate.toLatLng() }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLocate, DefaultMapCameraZoom)
    }

    LaunchedEffect(fieldsFillState) {
        snapshotFlow { fieldsFillState }
            .collectWithLifecycle(lifecycleOwner) {
                fieldsAllInputStateChange(fieldsFillState.all { true })
            }
    }

    RunningDatePickerDialog(
        visible = runningDatePickerDialogVisible,
        onDismissRequest = {
            runningDateState = runningDateState.newInstance()
            runningDatePickerDialogVisible = false
        },
        startDateIndex = DateCache.getCachedIndexFromDay(day = runningDateState.getDay()),
        startTimeType = runningDateState.getTimeType(),
        startHour = runningDateState.getHour(),
        startMinute = runningDateState.getMinute(),
        onRunningDateChange = { field ->
            with(runningDateState) {
                when (field) {
                    is RunningDate.Companion.Field.Date -> {
                        setDate(field.value)
                    }
                    is RunningDate.Companion.Field.TimeType -> {
                        setTimeType(field.value)
                    }
                    is RunningDate.Companion.Field.Hour -> {
                        setHour(field.value)
                    }
                    is RunningDate.Companion.Field.Minute -> {
                        setMinute(field.value)
                    }
                }
            }
        }
    )

    RunningTimePickerDialog(
        visible = runningTimePickerDialogVisible,
        onDismissRequest = { runningTimePickerDialogVisible = false },
        startHour = runningTimeState.hour,
        startMinute = runningTimeState.minute,
        runningTime = runningTimeState,
        onRunningTimeChange = { runningTime ->
            runningTimeState = runningTime
        }
    )

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.runningitemwrite_label_title),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        TextField(
            modifier = Modifier.padding(top = 12.dp),
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
        Divider(modifier = Modifier.padding(vertical = 20.dp), color = ColorAsset.G6)
        Text(
            text = stringResource(R.string.runningitemwrite_label_date),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize()
                .clip(DefaultFieldShape)
                .background(color = ColorAsset.G5_5)
                .clickable {
                    runningDatePickerDialogVisible = true
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
                    .background(color = ColorAsset.G5_5),
                text = runningDateState.toString(),
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
        Divider(modifier = Modifier.padding(vertical = 20.dp), color = ColorAsset.G6)
        Text(
            text = stringResource(R.string.runningitemwrite_label_running_time),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize()
                .clip(DefaultFieldShape)
                .background(color = ColorAsset.G5_5)
                .clickable {
                    runningTimePickerDialogVisible = true
                }
                .padding(horizontal = 32.dp)
        ) {
            val (clockIcon, dateString, arrowIcon) = createRefs()
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(clockIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_time_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .constrainAs(dateString) {
                        start.linkTo(clockIcon.end, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .background(color = ColorAsset.G5_5),
                text = runningTimeState.toString(),
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
        Divider(modifier = Modifier.padding(vertical = 20.dp), color = ColorAsset.G6)
        Row {
            Text(
                text = stringResource(R.string.runningitebwrite_label_gather_place),
                style = Typography.Body14R.copy(color = ColorAsset.G3_5)
            )
            Text(
                text = stringResource(R.string.runningitemwrite_hint_full_address_visibility),
                style = Typography.Body12R.copy(color = ColorAsset.PrimaryDarker)
            )
        }
        GoogleMap(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(220.dp),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                maxZoomPreference = 15f,
                minZoomPreference = 5f
            )
        ) {
            MarkerInfoWindow(
                position = myLocate,
                draggable = true,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_round_map_marker_24)
            ) { marker ->
                Text(
                    modifier = Modifier
                        .padding(start = 120.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 5.dp,
                                topEnd = 5.dp,
                                bottomEnd = 5.dp
                            )
                        )
                        .background(color = ColorAsset.G6),
                    text = marker.position.toAddress(context),
                    style = Typography.Custom.MapMarker
                )
            }
        }
    }
}
