/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FilterScreen.kt] created by Ji Sungbin on 22. 4. 5. 오후 4:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.filter.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.feature.home.filter.R
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.android.extension.bitmapDescriptorFromVector
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.android.extension.toLatLng
import team.applemango.runnerbe.shared.compose.component.LabelCheckbox
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.compose.component.TopBar
import team.applemango.runnerbe.shared.compose.default.RunnerbeCheckBoxDefaults
import team.applemango.runnerbe.shared.compose.default.RunnerbeToggleButtonDefaults
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.runIfBuilder

// FlowLayout 은 디자인이 안이쁘게 되서 직접 작성 함
private val jobLists = listOf(
    listOf(Job.PSV, Job.EDU, Job.DEV),
    listOf(Job.PSM, Job.DES),
    listOf(Job.MPR, Job.SER, Job.PRO),
    listOf(Job.RES, Job.SAF, Job.MED),
    listOf(Job.HUR, Job.ACC, Job.CUS)
)
private const val DefaultMapCameraZoom = 18f

@OptIn(
    LocalActivityUsageApi::class, // LocalActivity
    ExperimentalMaterialApi::class // RangeSlider
)
@Composable
internal fun FilterScreen(
    modifier: Modifier = Modifier
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val locate = remember { Me.locate.value.toLatLng() }

    var genderSelectState by remember { mutableStateOf(Me.genderFilter) }
    var ageRangeState by remember {
        mutableStateOf(
            Me.ageFilter.toFloatRange(
                defaultMin = 30f,
                defaultMax = 40f
            )
        )
    }
    var allAgeCheckState by remember {
        mutableStateOf(Me.ageFilter == AgeFilter.None)
    }
    var jobSelectState by remember {
        mutableStateOf(
            when (val jobFilter = Me.jobFilter) {
                JobFilter.None -> null
                else -> Job.values().first { it.name == jobFilter.code }
            }
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            locate,
            DefaultMapCameraZoom
        )
    }
    var allMeetDistanceCheckState by remember { mutableStateOf(true) }
    var meetDistanceState by remember {
        mutableStateOf(
            when (val distanceFilter = Me.distanceFilter) {
                DistanceFilter.None -> 0.5f
                else -> distanceFilter.value.toFloat()
            }
        )
    }

    @Stable
    fun resetState() {
        genderSelectState = Gender.All
        ageRangeState = 30f..40f
        allAgeCheckState = true
        jobSelectState = null
        allMeetDistanceCheckState = true
        meetDistanceState = 0.5f

        Me.genderFilter = Gender.All
        Me.ageFilter = AgeFilter.None
        Me.jobFilter = JobFilter.None
        Me.distanceFilter = DistanceFilter.None
    }

    Column(modifier = modifier) {
        TopBar(
            startContent = {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            activity.finishWithAnimation()
                        },
                    painter = painterResource(R.drawable.ic_round_arrow_left_24),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            centerContent = {
                Text(
                    text = stringResource(R.string.filter_title),
                    style = Typography.Body16R.copy(color = ColorAsset.G3)
                )
            },
            endContent = {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            resetState()
                        },
                    painter = painterResource(R.drawable.ic_round_refresh_24),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = stringResource(R.string.filter_label_gender),
                style = Typography.Body14R.copy(color = ColorAsset.G3_5)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Gender.values().forEach { gender ->
                    ToggleButton(
                        target = gender,
                        selectState = genderSelectState,
                        targetStringBuilder = { gender.string },
                        colors = RunnerbeToggleButtonDefaults.colors(),
                        textStyle = RunnerbeToggleButtonDefaults.textStyle(),
                    ) {
                        genderSelectState = gender
                        Me.genderFilter = gender
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
                    text = stringResource(R.string.filter_label_age_range),
                    style = Typography.Body14R.copy(color = ColorAsset.G3_5)
                )
                LabelCheckbox(
                    label = stringResource(R.string.filter_label_all_age_range),
                    labelStyle = Typography.Body12R.copy(color = ColorAsset.G3_5),
                    checkboxState = allAgeCheckState,
                    checkboxStateChange = { isAllAgeAllow ->
                        allAgeCheckState = isAllAgeAllow
                        if (isAllAgeAllow) {
                            Me.ageFilter = AgeFilter.None
                        } else {
                            Me.ageFilter = AgeFilter(
                                min = ageRangeState.start.toInt(),
                                max = ageRangeState.endInclusive.toInt()
                            )
                        }
                    },
                    checkboxColors = RunnerbeCheckBoxDefaults.colors(),
                )
            }
            RangeSlider(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = (30 - 16).dp
                    )
                    .fillMaxWidth(),
                enabled = !allAgeCheckState,
                values = ageRangeState,
                valueRange = 20f..65f,
                // steps = 5,
                onValueChange = { ageRange ->
                    ageRangeState = ageRange
                    Me.ageFilter = AgeFilter(
                        min = ageRange.start.toInt(),
                        max = ageRange.endInclusive.toInt()
                    )
                },
                colors = SliderDefaults.colors(
                    thumbColor = ColorAsset.PrimaryDarker,
                    disabledThumbColor = ColorAsset.G4,
                    activeTrackColor = ColorAsset.Primary,
                    inactiveTrackColor = ColorAsset.G5_5,
                    disabledActiveTrackColor = ColorAsset.G4_5,
                    disabledInactiveTrackColor = ColorAsset.G5_5,
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = ColorAsset.G6,
                    disabledActiveTickColor = Color.Transparent,
                    disabledInactiveTickColor = ColorAsset.G6,
                )
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
                modifier = Modifier.padding(vertical = 20.dp),
                color = ColorAsset.G6
            )
            Text(
                text = stringResource(R.string.filter_label_job),
                style = Typography.Body14R.copy(color = ColorAsset.G3_5)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(space = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                jobLists.forEach { jobList ->
                    Row(horizontalArrangement = Arrangement.spacedBy(space = 12.dp)) {
                        jobList.forEach { job ->
                            ToggleButton(
                                colors = RunnerbeToggleButtonDefaults.colors(),
                                target = job,
                                selectState = jobSelectState,
                                targetStringBuilder = { job.string }
                            ) {
                                if (jobSelectState == job) {
                                    jobSelectState = null
                                    Me.jobFilter = JobFilter.None
                                } else {
                                    jobSelectState = job
                                    Me.jobFilter = JobFilter.Create(job)
                                }
                            }
                        }
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
                    text = stringResource(R.string.filter_label_meet_place),
                    style = Typography.Body14R.copy(color = ColorAsset.G3_5)
                )
                LabelCheckbox(
                    label = stringResource(R.string.filter_label_all_distance),
                    labelStyle = Typography.Body12R.copy(color = ColorAsset.G3_5),
                    checkboxState = allMeetDistanceCheckState,
                    checkboxStateChange = { isAllDistanceAllow ->
                        allMeetDistanceCheckState = isAllDistanceAllow
                        if (isAllDistanceAllow) {
                            Me.distanceFilter = DistanceFilter.None
                        } else {
                            Me.distanceFilter = DistanceFilter.Create(meetDistanceState.toInt())
                        }
                    },
                    checkboxColors = RunnerbeCheckBoxDefaults.colors(),
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(8.dp)),
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
                    Marker(
                        state = rememberMarkerState(position = locate),
                        icon = context.bitmapDescriptorFromVector(R.drawable.ic_round_map_marker_24)
                    )
                    Circle(
                        center = locate,
                        fillColor = ColorAsset.Primary.copy(alpha = 0.5f),
                        strokeColor = ColorAsset.Primary,
                        visible = !allMeetDistanceCheckState,
                        radius = meetDistanceState * 1000.0, // 단위: meter
                    )
                }
                Slider(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    enabled = !allMeetDistanceCheckState,
                    value = meetDistanceState,
                    valueRange = 0.5f..5f,
                    steps = 4,
                    onValueChange = { meetDistance ->
                        meetDistanceState = meetDistance
                        Me.distanceFilter = DistanceFilter.Create(meetDistance.toInt())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = ColorAsset.PrimaryDarker,
                        disabledThumbColor = ColorAsset.G4,
                        activeTrackColor = ColorAsset.Primary,
                        inactiveTrackColor = ColorAsset.G5_5,
                        disabledActiveTrackColor = ColorAsset.G4_5,
                        disabledInactiveTrackColor = ColorAsset.G5_5,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = ColorAsset.G6,
                        disabledActiveTickColor = Color.Transparent,
                        disabledInactiveTickColor = ColorAsset.G6,
                    )
                )
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    visible = !allMeetDistanceCheckState
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${
                        meetDistanceState
                            .toInt()
                            .toString()
                            .runIfBuilder(
                                condition = { it == "0" },
                                run = { "0.5" }
                            )
                        }km 까지",
                        style = Typography.Body14R.copy(
                            color = ColorAsset.G3_5,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}
