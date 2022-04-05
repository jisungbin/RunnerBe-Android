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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
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
import team.applemango.runnerbe.shared.compose.default.RunnerbeSliderDefaults
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
private const val DefaultMapCameraZoom = 15f

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

    val ageFilterState by Me.ageFilter.collectAsState()
    val jobFilterState by Me.jobFilter.collectAsState()
    val distanceFilterState by Me.distanceFilter.collectAsState()

    val genderSelectState by Me.genderFilter.collectAsState()
    var ageRangeState by remember {
        mutableStateOf(
            ageFilterState.toFloatRange(
                defaultMin = 30f,
                defaultMax = 40f
            )
        )
    }
    val allAgeCheckState = ageFilterState == AgeFilter.None
    val jobSelectState = when (jobFilterState) {
        JobFilter.None -> null
        else -> jobFilterState.toJob()
    }
    val allMeetDistanceCheckState = distanceFilterState == DistanceFilter.None
    var meetDistanceState by remember {
        mutableStateOf(
            when (distanceFilterState) {
                DistanceFilter.None -> 0.5f
                else -> distanceFilterState.value.toFloat()
            }
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            locate,
            DefaultMapCameraZoom
        )
    }

    @Stable
    fun resetState() {
        ageRangeState = 30f..40f
        meetDistanceState = 0.5f
        Me.updateGenderFilter(Gender.All)
        Me.updateAgeFilter(AgeFilter.None)
        Me.updateJobFilter(JobFilter.None)
        Me.updateDistanceFilter(DistanceFilter.None)
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
                        Me.updateGenderFilter(gender)
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
                    checkboxCheckedChange = { isAllAgeAllow ->
                        if (isAllAgeAllow) {
                            Me.updateAgeFilter(AgeFilter.None)
                        } else {
                            Me.updateAgeFilter(
                                AgeFilter(
                                    min = ageRangeState.start.toInt(),
                                    max = ageRangeState.endInclusive.toInt()
                                )
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
                    Me.updateAgeFilter(
                        AgeFilter(
                            min = ageRange.start.toInt(),
                            max = ageRange.endInclusive.toInt()
                        )
                    )
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
                                    Me.updateJobFilter(JobFilter.None)
                                } else {
                                    Me.updateJobFilter(JobFilter.Create(job))
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
                    checkboxCheckedChange = { isAllDistanceAllow ->
                        if (isAllDistanceAllow) {
                            Me.updateDistanceFilter(DistanceFilter.None)
                        } else {
                            Me.updateDistanceFilter(DistanceFilter.Create(meetDistanceState.toInt()))
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
                        Me.updateDistanceFilter(DistanceFilter.Create(meetDistance.toInt()))
                    },
                    colors = RunnerbeSliderDefaults.colors(),
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
