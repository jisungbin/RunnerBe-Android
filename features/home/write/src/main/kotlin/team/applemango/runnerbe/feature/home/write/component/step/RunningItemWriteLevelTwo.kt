/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteLevelTwo.kt] created by Ji Sungbin on 22. 3. 19. 오전 4:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.RunningItemWriteViewModel
import team.applemango.runnerbe.feature.home.write.util.extension.toLatLng
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

private const val DefaultMapCameraZoom = 7f

@Composable
internal fun RunningItemWriteLevelTwo(
    modifier: Modifier = Modifier,
    vm: RunningItemWriteViewModel = viewModel(),
) {

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
            val (map, runningItemType, title, informations) = createRefs()
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
                        height = 52.dp
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
                modifier = Modifier.constrainAs(informations) {
                    start.linkTo(runningItemType.start)
                    bottom.linkTo(map.bottom)
                }
            ) {
                // TODO
            }
        }
    }
}
