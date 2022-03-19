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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.RunningItemWriteViewModel
import team.applemango.runnerbe.feature.home.write.component.CircleBorderText
import team.applemango.runnerbe.feature.home.write.constant.PeopleCountErrorType
import team.applemango.runnerbe.feature.home.write.util.extension.toLatLng
import team.applemango.runnerbe.shared.compose.component.IconText
import team.applemango.runnerbe.shared.compose.component.LabelCheckbox
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.compose.default.RunnerbeCheckBoxDefaults
import team.applemango.runnerbe.shared.compose.default.RunnerbeRangePickerDefaults
import team.applemango.runnerbe.shared.compose.default.RunnerbeToggleButtonDefaults
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.xml.rangepicker.RangePicker

private const val DefaultMapCameraZoom = 7f

@Composable
internal fun RunningItemWriteLevelTwo(
    modifier: Modifier = Modifier,
    vm: RunningItemWriteViewModel = activityViewModel(),
) {
    var ageRange = remember { 30f..40f }

    var genderSelectState by remember { mutableStateOf<Gender?>(null) }
    var allAgeCheckState by remember { mutableStateOf(false) }
    var peopleCountState by remember { mutableStateOf(4) }
    var peopleCountErrorTypeState by remember { mutableStateOf(PeopleCountErrorType.None) }
    var messageFieldState by remember { mutableStateOf(TextFieldValue()) }

    val circleBorderTextColorState = animatedColorState(
        target = peopleCountErrorTypeState,
        selectState = PeopleCountErrorType.None,
        defaultColor = ColorAsset.G4_5,
        selectedColor = ColorAsset.G3_5
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            vm.locate.toLatLng(),
            DefaultMapCameraZoom
        )
    }

    Column(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (map, runningItemType, title, information) = createRefs()
            GoogleMap(
                modifier = Modifier
                    .constrainAs(map) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .size(
                        width = 110.dp,
                        height = 60.dp
                    ),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    indoorLevelPickerEnabled = false,
                    mapToolbarEnabled = false,
                    myLocationButtonEnabled = false,
                    rotationGesturesEnabled = false,
                    scrollGesturesEnabled = false,
                    scrollGesturesEnabledDuringRotateOrZoom = false,
                    tiltGesturesEnabled = false,
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = false,
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = ColorAsset.G5_5)
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(R.drawable.ic_round_place_24),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Text(
                            text = vm.locate.address.split(" ").take(2).joinToString(""),
                            style = Typography.Body12R.copy(color = ColorAsset.G3)
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .constrainAs(runningItemType) {
                        start.linkTo(map.end, 18.dp)
                        top.linkTo(map.top)
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = ColorAsset.PrimaryDarker)
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                text = vm.runningItemType.toString(),
                style = Typography.Custom.WriteRunningItemType
            )
            Text(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(runningItemType.start)
                    top.linkTo(runningItemType.bottom, 4.dp)
                },
                text = vm.title,
                style = Typography.Body16R.copy(color = ColorAsset.PrimaryDarker)
            )
            Row(
                modifier = Modifier.constrainAs(information) {
                    start.linkTo(runningItemType.start)
                    bottom.linkTo(map.bottom)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                .graphicsLayer(clip = false)
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            color = ColorAsset.G6,
            thickness = 10.dp
        )
        Text(
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
            items(Gender.values()) { gender ->
                ToggleButton(
                    colors = RunnerbeToggleButtonDefaults.colors(),
                    target = gender,
                    selectState = genderSelectState,
                    targetStringBuilder = { gender.string }
                ) {
                    genderSelectState = gender
                }
            }
        }
        Divider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = ColorAsset.G6
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_label_age_range),
                style = Typography.Body14R.copy(color = ColorAsset.G3_5)
            )
            LabelCheckbox(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.runningitemwrite_label_all_age_range),
                labelStyle = Typography.Body12R.copy(color = ColorAsset.G3_5),
                checkboxState = allAgeCheckState,
                checkboxStateChange = { state ->
                    allAgeCheckState = state
                },
                checkboxColors = RunnerbeCheckBoxDefaults.colors(),
            )
        }
        RangePicker( // TODO: default colors
            modifier = Modifier.padding(top = 12.dp),
            enabled = !allAgeCheckState,
            range = 20f..65f,
            value = ageRange,
            trackOption = RunnerbeRangePickerDefaults.track(),
            thumbOption = RunnerbeRangePickerDefaults.thumb(),
            tickOption = RunnerbeRangePickerDefaults.tick(),
            onValueChange = { newAgeRange ->
                ageRange = newAgeRange
            }
        )
        Divider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = ColorAsset.G6
        )
        Text(
            text = stringResource(R.string.runningitemwrite_label_people_count),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        Column(
            modifier = Modifier
                .padding(top = 12.dp)
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
                    style = Typography.Title18R.copy(color = circleBorderTextColorState),
                    borderColor = circleBorderTextColorState,
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
                    style = Typography.EngBody16R.copy(color = ColorAsset.Primary)
                )
                CircleBorderText(
                    enabled = peopleCountErrorTypeState != PeopleCountErrorType.Max,
                    text = "+",
                    style = Typography.Title18R.copy(color = circleBorderTextColorState),
                    borderColor = circleBorderTextColorState,
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
            modifier = Modifier.padding(vertical = 20.dp),
            color = ColorAsset.G6
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                .padding(top = 12.dp, bottom = 45.dp)
                .fillMaxWidth()
                .height(160.dp),
            shape = RoundedCornerShape(8.dp),
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
