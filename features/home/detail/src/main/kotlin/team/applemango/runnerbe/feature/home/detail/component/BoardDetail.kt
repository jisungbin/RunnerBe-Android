/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardDetail.kt] created by Ji Sungbin on 22. 3. 22. 오후 10:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.feature.home.detail.DetailViewModel
import team.applemango.runnerbe.feature.home.detail.R
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.compose.component.BorderOption
import team.applemango.runnerbe.shared.compose.component.CircleBorderText
import team.applemango.runnerbe.shared.compose.component.IconText
import team.applemango.runnerbe.shared.compose.component.TopBar
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.constant.CenterDot
import team.applemango.runnerbe.shared.domain.extension.runIfBuilder
import team.applemango.runnerbe.shared.domain.extension.toRunningDateString

@Immutable
private data class InformationItem(
    @DrawableRes val iconRes: Int,
    val text: String,
)

// TODO: 신청자 관리 (BottomSheet or LazyColumn 결정 필요)

@OptIn(LocalActivityUsageApi::class) // LocalActivity.current, activityViewModel
@Composable
internal fun BoardDetail(
    modifier: Modifier = Modifier,
    runningItemInformation: RunningItemInformation,
    vm: DetailViewModel = activityViewModel(),
) {
    val activity = LocalActivity.current

    val bookmarkIconRes = remember(runningItemInformation.bookmarked) {
        when (runningItemInformation.bookmarked) {
            true -> R.drawable.ic_round_bookmark_24
            else -> R.drawable.ic_outlined_bookmark_24
        }
    }

    val informationItems = remember {
        listOf(
            InformationItem(
                iconRes = R.drawable.ic_round_place_24,
                text = runningItemInformation.item.locate.address
            ),
            InformationItem(
                iconRes = R.drawable.ic_outlined_scheduled_24,
                text = runningItemInformation.item.meetingDate.toRunningDateString()
            ),
            InformationItem(
                iconRes = R.drawable.ic_outlined_time_24,
                text = runningItemInformation.item.runningTime.toString()
            ),
            InformationItem(
                iconRes = R.drawable.ic_round_people_24,
                text = run {
                    val gender = runningItemInformation
                        .item
                        .gender
                        .string
                        .runIfBuilder(
                            condition = { gender ->
                                gender != Gender.All.string
                            },
                            run = {
                                "${this}만"
                            }
                        )

                    val ageRange = runningItemInformation.item.ageFilter.toString()
                    val maxPeopleCount = runningItemInformation.item.maxRunnerCount
                    "$gender $CenterDot $ageRange $CenterDot 최대 ${maxPeopleCount}명"
                }
            )
        )
    }

    ConstraintLayout(modifier = modifier) {
        val (content, bottomCTAButtons) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomCTAButtons.top, 50.dp)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
        ) {
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
                        text = runningItemInformation.item.runningType.toString(),
                        style = Typography.Body16R.copy(color = ColorAsset.G3)
                    )
                },
                endContent = {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                // TODO: report
                            },
                        painter = painterResource(R.drawable.ic_round_report_24),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            )
            GoogleMap(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
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
                // TODO: Marker, Circle
            }
            CircleBorderText(
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 8.dp
                ),
                text = runningItemInformation.item.runningType.toString(),
                style = Typography.Body12R.copy(color = ColorAsset.PrimaryDark),
                borderOption = BorderOption(color = ColorAsset.PrimaryDark),
            )
            Text(
                text = runningItemInformation.item.title,
                style = Typography.Custom.RunningItemDetailTitle
            )
            Divider(
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 16.dp
                ),
                color = ColorAsset.G5_5
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(items = informationItems) { information ->
                    IconText(
                        iconRes = information.iconRes,
                        text = information.text
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 16.dp
                ),
                color = ColorAsset.G5_5
            )
            Text(
                text = runningItemInformation.item.message,
                style = Typography.Body14R.copy(color = ColorAsset.G2_5)
            )
        }
        ConstraintLayout(
            modifier = Modifier
                .constrainAs(bottomCTAButtons) {
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                    bottom.linkTo(parent.bottom)
                }
                .background(color = ColorAsset.G6)
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        ) {
            val (bookmark, join) = createRefs()

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        color = ColorAsset.Primary,
                        width = 1.dp,
                        shape = CircleShape
                    )
                    .constrainAs(bookmark) {
                        start.linkTo(parent.start)
                        width = Dimension.value(40.dp)
                    }.clickable {
                        // TODO: update bookmark state
                    },
                painter = painterResource(bookmarkIconRes),
                contentDescription = null
            )
            Button(
                modifier = Modifier
                    .constrainAs(join) {
                        start.linkTo(bookmark.end, 12.dp)
                        width = Dimension.value(40.dp)
                    }
                    .clip(RoundedCornerShape(24.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorAsset.Primary
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = stringResource(R.string.detail_button_register),
                    style = Typography.Body14M.copy(color = Color.Black)
                )
            }
        }
    }
}
