/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteLevelTwo.kt] created by Ji Sungbin on 22. 3. 19. 오전 4:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component.step

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.RangeSlider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.RunningItemWriteViewModel
import team.applemango.runnerbe.feature.home.write.constant.PeopleCountErrorType
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.android.extension.bitmapDescriptorFromVector
import team.applemango.runnerbe.shared.android.extension.toAddress
import team.applemango.runnerbe.shared.android.extension.toLatLng
import team.applemango.runnerbe.shared.compose.component.BorderOption
import team.applemango.runnerbe.shared.compose.component.CircleBorderText
import team.applemango.runnerbe.shared.compose.component.IconText
import team.applemango.runnerbe.shared.compose.component.LabelCheckbox
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.compose.default.RunnerbeCheckBoxDefaults
import team.applemango.runnerbe.shared.compose.default.RunnerbeSliderDefaults
import team.applemango.runnerbe.shared.compose.default.RunnerbeToggleButtonDefaults
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState
import team.applemango.runnerbe.shared.domain.constant.EmptyString

private const val DefaultMapCameraZoom = 12f

@OptIn(LocalActivityUsageApi::class, ExperimentalMaterialApi::class) // activityViewModel()
@Composable
internal fun RunningItemWriteLevelTwo(
    modifier: Modifier = Modifier,
    vm: RunningItemWriteViewModel = activityViewModel(),
) {
    val locate = remember { Me.locate.value.toLatLng() }
    val context = LocalContext.current.applicationContext

    var ageRangeState by remember { mutableStateOf(30f..40f) }
    var genderSelectState by remember { mutableStateOf<Gender?>(null) }
    var allAgeCheckState by remember { mutableStateOf(false) }
    var peopleCountState by remember { mutableStateOf(4) }
    var peopleCountErrorTypeState by remember { mutableStateOf(PeopleCountErrorType.None) }
    var messageFieldState by remember { mutableStateOf(TextFieldValue()) }
    val peopleCountMinusColorState = animatedColorState(
        target = peopleCountErrorTypeState,
        selectState = PeopleCountErrorType.Min,
        defaultColor = ColorAsset.G3_5,
        selectedColor = ColorAsset.G4_5
    )
    val peopleCountPlusColorState = animatedColorState(
        target = peopleCountErrorTypeState,
        selectState = PeopleCountErrorType.Max,
        defaultColor = ColorAsset.G3_5,
        selectedColor = ColorAsset.G4_5
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            locate,
            DefaultMapCameraZoom
        )
    }

    Column(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (map, mapCover, title, information) = createRefs()

            GoogleMap(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(map) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .size(
                        width = 150.dp,
                        height = 100.dp
                    ),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    indoorLevelPickerEnabled = false,
                    mapToolbarEnabled = false,
                    myLocationButtonEnabled = false,
                    rotationGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    scrollGesturesEnabledDuringRotateOrZoom = true,
                    tiltGesturesEnabled = true,
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = true,
                ),
                properties = MapProperties(
                    maxZoomPreference = 15f,
                    minZoomPreference = 5f
                )
            ) {
                MarkerInfoWindow(
                    state = rememberMarkerState(position = locate),
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
            Row(
                modifier = Modifier
                    .constrainAs(mapCover) {
                        start.linkTo(map.start, 16.dp)
                        end.linkTo(map.end, 16.dp)
                        bottom.linkTo(map.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .background(color = ColorAsset.G5_5)
                    .padding(
                        vertical = 2.dp,
                        horizontal = 4.dp
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_round_place_24),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    text = locate.toAddress(context).split(" ").take(2).joinToString(""),
                    style = Typography.Body12R.copy(color = ColorAsset.G3)
                )
            }
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(map.end)
                        top.linkTo(map.top)
                        end.linkTo(parent.end, 16.dp)

                        width = Dimension.fillToConstraints
                    },
                text = vm.title,
                style = Typography.Body16R.copy(color = ColorAsset.PrimaryDarker)
            )
            Column(
                modifier = Modifier
                    .constrainAs(information) {
                        start.linkTo(title.start)
                        end.linkTo(title.end)
                        top.linkTo(title.bottom)
                        bottom.linkTo(map.bottom)

                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconText(
                    iconRes = R.drawable.ic_round_schedule_24,
                    iconSize = 18.dp,
                    text = vm.runningDate.toString(),
                    textStyle = Typography.Body12R.copy(color = ColorAsset.G3)
                )
                IconText(
                    iconRes = R.drawable.ic_round_time_24,
                    iconSize = 18.dp,
                    text = vm.runningTime.toString(withWhitespace = false),
                    textStyle = Typography.Body12R.copy(color = ColorAsset.G3)
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            color = ColorAsset.G6,
            thickness = 10.dp
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.runningitemwrite_label_gender),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            items(items = Gender.values()) { gender ->
                ToggleButton(
                    target = gender,
                    selectState = genderSelectState,
                    targetStringBuilder = { gender.string },
                    colors = RunnerbeToggleButtonDefaults.colors(),
                    textStyle = RunnerbeToggleButtonDefaults.textStyle(),
                ) {
                    genderSelectState = gender
                }
            }
        }
        Divider(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
            color = ColorAsset.G6
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_label_age_range),
                style = Typography.Body14R.copy(color = ColorAsset.G3_5)
            )
            LabelCheckbox(
                label = stringResource(R.string.runningitemwrite_label_all_age_range),
                labelStyle = Typography.Body12R.copy(color = ColorAsset.G3_5),
                checkboxState = allAgeCheckState,
                checkboxCheckedChange = { state ->
                    allAgeCheckState = state
                },
                checkboxColors = RunnerbeCheckBoxDefaults.colors(),
            )
        }
        /*RangePicker(
            modifier = Modifier.padding(top = 12.dp),
            enabled = !allAgeCheckState,
            range = 20f..65f,
            value = ageRangeState,
            trackOption = RunnerbeRangePickerDefaults.track(),
            thumbOption = RunnerbeRangePickerDefaults.thumb(),
            tickOption = RunnerbeRangePickerDefaults.tick(),
            onValueChange = { newAgeRange ->
                ageRangeState = newAgeRange
            }
        )*/
        RangeSlider(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            enabled = !allAgeCheckState,
            values = ageRangeState,
            valueRange = 20f..65f,
            // steps = 5,
            onValueChange = { ageRange ->
                ageRangeState = ageRange
            },
            colors = RunnerbeSliderDefaults.colors(),
        )
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            visible = !allAgeCheckState
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${ageRangeState.start.toInt()}세 ~ ${ageRangeState.endInclusive.toInt()}세",
                style = Typography.Body14R.copy(
                    color = ColorAsset.G3_5,
                    textAlign = TextAlign.Center
                )
            )
        }
        Divider(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
            color = ColorAsset.G6
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.runningitemwrite_label_people_count),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                CircleBorderText(
                    enabled = peopleCountErrorTypeState != PeopleCountErrorType.Min,
                    text = "-",
                    style = Typography.Title20R.copy(color = peopleCountMinusColorState),
                    borderOption = BorderOption(color = peopleCountMinusColorState),
                    onClick = {
                        peopleCountErrorTypeState = if (peopleCountState > 2) {
                            peopleCountState--
                            PeopleCountErrorType.None
                        } else {
                            PeopleCountErrorType.Min
                        }
                    }
                )
                Text(
                    text = peopleCountState.toString(),
                    style = Typography.Title18R.copy(color = ColorAsset.Primary)
                )
                CircleBorderText(
                    enabled = peopleCountErrorTypeState != PeopleCountErrorType.Max,
                    text = "+",
                    style = Typography.Title20R.copy(color = peopleCountPlusColorState),
                    borderOption = BorderOption(color = peopleCountPlusColorState),
                    onClick = {
                        peopleCountErrorTypeState = if (peopleCountState < 8) {
                            peopleCountState++
                            PeopleCountErrorType.None
                        } else {
                            PeopleCountErrorType.Max
                        }
                    }
                )
            }
            AnimatedVisibility(
                modifier = Modifier.padding(top = 20.dp),
                visible = peopleCountErrorTypeState != PeopleCountErrorType.None
            ) {
                Text(
                    text = when (peopleCountErrorTypeState) {
                        PeopleCountErrorType.Min -> stringResource(R.string.runningitemwrite_error_min_people_count)
                        PeopleCountErrorType.Max -> stringResource(R.string.runningitemwrite_error_max_people_count)
                        PeopleCountErrorType.None -> EmptyString
                    },
                    style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
                )
            }
        }
        Divider(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
            color = ColorAsset.G6
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_label_message),
                style = Typography.Body14R.copy(color = ColorAsset.G3_5)
            )
            Text(
                text = "${messageFieldState.text.length}/500",
                style = Typography.Body14R.copy(
                    color = when (messageFieldState.text.length == 500) {
                        true -> ColorAsset.ErrorLight
                        else -> ColorAsset.G4
                    }
                )
            )
        }
        TextField(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    bottom = 16.dp
                )
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(160.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = ColorAsset.G1,
                backgroundColor = ColorAsset.G5_5,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            value = messageFieldState,
            onValueChange = { message ->
                if (message.text.length <= 500) {
                    messageFieldState = message
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.runningitemwrite_placeholder_message),
                    style = Typography.Body16R.copy(color = ColorAsset.G3_5)
                )
            },
            textStyle = Typography.Body16R.copy(color = ColorAsset.G1)
        )
    }
}
