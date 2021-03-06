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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import androidx.datastore.preferences.core.edit
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.RunningItemWriteViewModel
import team.applemango.runnerbe.feature.home.write.component.RunningDatePickerDialog
import team.applemango.runnerbe.feature.home.write.component.RunningTimePickerDialog
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.model.RunningTime
import team.applemango.runnerbe.feature.home.write.util.DateCache
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.android.extension.bitmapDescriptorFromVector
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.android.extension.toAddress
import team.applemango.runnerbe.shared.android.extension.toLatLng
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

private const val DefaultMapCameraZoom = 10f
private val DefaultFieldShape = RoundedCornerShape(8.dp)
private val DefaultFieldHeight = 58.dp

// TODO: Date, Time 데이터 저장
@OptIn(
    LocalActivityUsageApi::class, // activityViewModel()
    FlowPreview::class // Flow<T>.debounce
)
@Composable
internal fun RunningItemWriteLevelOne(
    modifier: Modifier = Modifier,
    runningItemType: RunningItemType,
    restoreLastData: Boolean,
    vm: RunningItemWriteViewModel = activityViewModel(),
    inputStateChanged: (state: Boolean) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current

    /*var*/
    val locateState by remember { mutableStateOf(Me.locate.value.toLatLng()) }
    var titleFieldState by remember { mutableStateOf(TextFieldValue()) }
    var isRunningDateEdited by remember { mutableStateOf(false) }
    var runningDateState by remember { mutableStateOf(RunningDate.getDefault(runningItemType)) }
    // var runningDateStateForSaving by remember { mutableStateOf(RunningDate()) }
    var runningTimeState by remember { mutableStateOf(RunningTime(hour = 0, minute = 20)) }
    var runningDatePickerDialogVisibleState by remember { mutableStateOf(false) }
    var runningTimePickerDialogVisibleState by remember { mutableStateOf(false) }
    var titleErrorVisibleState by remember { mutableStateOf(false) }
    val locateMarkerState = rememberMarkerState(position = locateState)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            locateState,
            DefaultMapCameraZoom
        )
    }

    RunningDatePickerDialog(
        visible = runningDatePickerDialogVisibleState,
        onDismissRequest = {
            runningDatePickerDialogVisibleState = false
        },
        startDateIndex = DateCache.getCachedIndexFromDay(day = runningDateState.getDay()),
        startTimeType = runningDateState.getTimeType(),
        startHour = runningDateState.getHour(),
        startMinute = runningDateState.getMinute(),
        onRunningDateChange = { field ->
            isRunningDateEdited = true
            /*runningDateStateForSaving = */with(runningDateState) {
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
                this
            }
            vm.runningDate = runningDateState
        }
    )

    RunningTimePickerDialog(
        visible = runningTimePickerDialogVisibleState,
        onDismissRequest = { runningTimePickerDialogVisibleState = false },
        startHour = runningTimeState.hour,
        startMinute = runningTimeState.minute,
        runningTime = runningTimeState,
        onRunningTimeChange = { runningTime ->
            runningTimeState = runningTime
            vm.runningTime = runningTimeState
        }
    )

    LaunchedEffect(Unit) {
        snapshotFlow { titleFieldState }
            .defaultCatch(action = vm::emitException)
            .debounce(300)
            .collectWithLifecycle(lifecycleOwner = lifecycleOwner) { title ->
                context.dataStore.edit { preference ->
                    preference[DataStoreKey.Write.Title] = title.text
                }
            }

        /*snapshotFlow { runningDateStateForSaving }
            .defaultCatch(action = vm::emitException)
            .debounce(300)
            .collectWithLifecycle(lifecycleOwner = lifecycleOwner) { runningDate ->
                context.dataStore.edit { preference ->
                    preference[DataStoreKey.Write.RunningDate] = runningDate.toDate().time
                }
            }

        snapshotFlow { runningTimeState }
            .defaultCatch(action = vm::emitException)
            .debounce(300)
            .collectWithLifecycle(lifecycleOwner = lifecycleOwner) { runningTime ->
                context.dataStore.edit { preference ->
                    preference[DataStoreKey.Write.RunningTime] = runningTime.toKey()
                }
            }

        snapshotFlow { locateMarkerState.position }
            .defaultCatch(action = vm::emitException)
            .debounce(300)
            .collectWithLifecycle(lifecycleOwner = lifecycleOwner) { locate ->
                context.dataStore.edit { preference ->
                    preference[DataStoreKey.Write.RunningTime] = locate.toKey()
                }
                vm.locate = Locate(
                    address = locate.toAddress(context),
                    latitude = locate.latitude,
                    longitude = locate.longitude
                )
            }*/
    }

    LaunchedEffect(runningItemType) {
        snapshotFlow { runningItemType }
            .defaultCatch(action = vm::emitException)
            .collectWithLifecycle(lifecycleOwner = lifecycleOwner) { type ->
                if (!isRunningDateEdited) {
                    runningDateState = RunningDate.getDefault(type)
                }
            }
    }

    LaunchedEffect(restoreLastData) {
        if (restoreLastData) {
            val preference = context
                .dataStore
                .data
                .defaultCatch(action = vm::emitException)
                .first()

            preference[DataStoreKey.Write.Title]?.let { restoreTitle ->
                titleFieldState = TextFieldValue(restoreTitle)
            }
            /*preference[DataStoreKey.Write.RunningDate]?.let { restoreRunningDateTime ->
                runningDateState = RunningDate(Date().apply { time = restoreRunningDateTime })
            }
            preference[DataStoreKey.Write.RunningTime]?.let { restoreRunningTimeKey ->
                runningTimeState = RunningTime.fromKey(restoreRunningTimeKey)
            }
            preference[DataStoreKey.Write.Locate]?.let { restoreLocateKey ->
                locateState = latLngFromKey(restoreLocateKey)
            }*/
        }
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.runningitemwrite_label_title),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        TextField(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(DefaultFieldHeight),
            value = titleFieldState,
            shape = DefaultFieldShape,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = ColorAsset.G1,
                backgroundColor = ColorAsset.G5_5,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = { newTitleValue ->
                if (newTitleValue.text.isNotEmpty()) {
                    inputStateChanged(true)
                } else {
                    inputStateChanged(false)
                }
                if (newTitleValue.text.length <= 30) {
                    titleFieldState = newTitleValue
                    vm.title = newTitleValue.text
                    titleErrorVisibleState = false
                } else {
                    titleErrorVisibleState = true
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
            visible = titleErrorVisibleState
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_error_max_title_length),
                style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = ColorAsset.G6
        )
        Text(
            text = stringResource(R.string.runningitemwrite_label_date),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(DefaultFieldHeight)
                .clip(DefaultFieldShape)
                .background(color = ColorAsset.G5_5)
                .clickable {
                    runningDatePickerDialogVisibleState = true
                }
                .padding(horizontal = 16.dp)
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
                    .size(16.dp)
                    .constrainAs(arrowIcon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_chevron_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = ColorAsset.G6
        )
        Text(
            text = stringResource(R.string.runningitemwrite_label_running_time),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(DefaultFieldHeight)
                .clip(DefaultFieldShape)
                .background(color = ColorAsset.G5_5)
                .clickable {
                    runningTimePickerDialogVisibleState = true
                }
                .padding(horizontal = 16.dp)
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
                    .size(16.dp)
                    .constrainAs(arrowIcon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_chevron_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = ColorAsset.G6
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                .height(220.dp)
                .clip(DefaultFieldShape),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                indoorLevelPickerEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = true,
                rotationGesturesEnabled = true,
                scrollGesturesEnabled = true,
                scrollGesturesEnabledDuringRotateOrZoom = true,
                tiltGesturesEnabled = true,
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true,
            ),
        ) {
            MarkerInfoWindow(
                state = locateMarkerState,
                draggable = true,
                icon = context.bitmapDescriptorFromVector(R.drawable.ic_round_map_marker_24)
            ) { marker ->
                Text(
                    modifier = Modifier
                        .padding(start = 150.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 5.dp,
                                topEnd = 5.dp,
                                bottomEnd = 5.dp
                            )
                        )
                        .background(color = ColorAsset.G6)
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                    text = marker.position.toAddress(context),
                    style = Typography.Custom.MapMarker
                )
            }
        }
    }
}
