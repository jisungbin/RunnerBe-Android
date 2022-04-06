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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.model.common.Time
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.feature.home.detail.DetailViewModel
import team.applemango.runnerbe.feature.home.detail.R
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.android.extension.toLatLng
import team.applemango.runnerbe.shared.compose.component.BorderOption
import team.applemango.runnerbe.shared.compose.component.CircleBorderContent
import team.applemango.runnerbe.shared.compose.component.IconText
import team.applemango.runnerbe.shared.compose.component.RoundBorderText
import team.applemango.runnerbe.shared.compose.component.TopBar
import team.applemango.runnerbe.shared.compose.default.RunnerbePlaceholderDefaults
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.constant.CenterDot
import team.applemango.runnerbe.shared.domain.extension.runIfBuilder
import team.applemango.runnerbe.shared.domain.extension.toRunningDateString
import java.util.Date
import kotlin.random.Random

@Immutable
private data class InformationItem(
    @DrawableRes val iconRes: Int,
    val text: String,
)

// TODO: 신청자 관리

@OptIn(LocalActivityUsageApi::class) // LocalActivity, activityViewModel
@Composable
internal fun BoardDetail(
    modifier: Modifier = Modifier,
    runningItemInformation: RunningItemInformation,
    placeholderEnabled: Boolean = false,
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
                            .placeholder(
                                visible = placeholderEnabled,
                                color = RunnerbePlaceholderDefaults.BaseColor,
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                )
                            )
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
                        modifier = Modifier
                            .wrapContentSize()
                            .placeholder(
                                visible = placeholderEnabled,
                                color = RunnerbePlaceholderDefaults.BaseColor,
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                )
                            ),
                        text = runningItemInformation.item.runningType.toString(),
                        style = Typography.Body16R.copy(color = ColorAsset.G3)
                    )
                },
                endContent = {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .placeholder(
                                visible = placeholderEnabled,
                                color = RunnerbePlaceholderDefaults.BaseColor,
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                )
                            )
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
                    .fillMaxWidth()
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
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
                )
            ) {
                Circle(
                    center = runningItemInformation.item.locate.toLatLng(),
                    fillColor = ColorAsset.Primary.copy(alpha = 0.5f),
                    strokeColor = ColorAsset.Primary,
                    radius = runningItemInformation.item.distance * 1000.0, // 단위: meter
                )
            }
            RoundBorderText(
                modifier = Modifier
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
                text = runningItemInformation.item.runningType.toString(),
                style = Typography.Body12R.copy(color = ColorAsset.PrimaryDark),
                borderOption = BorderOption(color = ColorAsset.PrimaryDark),
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
                text = runningItemInformation.item.title,
                style = Typography.Custom.RunningItemDetailTitle
            )
            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 20.dp,
                        horizontal = 16.dp
                    )
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
                color = ColorAsset.G5_5
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(items = informationItems) { information ->
                    IconText(
                        modifier = Modifier
                            .wrapContentSize()
                            .placeholder(
                                visible = placeholderEnabled,
                                color = RunnerbePlaceholderDefaults.BaseColor,
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                )
                            ),
                        iconRes = information.iconRes,
                        text = information.text,
                        textStyle = Typography.Body14R.copy(color = ColorAsset.G1),
                        textStartPadding = 8.dp
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 20.dp,
                        horizontal = 16.dp
                    )
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
                color = ColorAsset.G5_5
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
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

            CircleBorderContent(
                modifier = Modifier
                    .constrainAs(bookmark) {
                        start.linkTo(parent.start)
                        width = Dimension.value(40.dp)
                    }
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
                onClick = {
                    // TODO: update bookmark state
                },
                borderOption = BorderOption(color = ColorAsset.Primary),
            ) {
                Icon(
                    painter = painterResource(bookmarkIconRes),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Button(
                modifier = Modifier
                    .constrainAs(join) {
                        start.linkTo(bookmark.end, 12.dp)
                        end.linkTo(parent.end)
                        width = Dimension.value(40.dp)
                        height = Dimension.fillToConstraints
                    }
                    .clip(RoundedCornerShape(24.dp))
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
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

// Dummy Composable for Placeholder
@Composable
internal fun BoardDetailDummy(
    modifier: Modifier = Modifier,
    placeholderEnabled: Boolean = true,
) {
    BoardDetail(
        modifier = modifier,
        runningItemInformation = RunningItemInformation(
            isMyItem = false,
            bookmarked = false,
            joinRunners = emptyList(),
            waitingRunners = emptyList(),
            item = RunningItem(
                itemId = Random.nextInt(),
                ownerId = Random.nextInt(),
                ownerNickName = " ".repeat(5),
                ownerProfileImageUrl = "null",
                createdAt = Date(),
                bookmarkCount = Random.nextInt(),
                runningType = RunningItemType.values().random(),
                finish = false,
                maxRunnerCount = Random.nextInt(),
                title = " ".repeat(10),
                gender = Gender.values().random(),
                jobs = Job.values().toList(),
                ageFilter = AgeFilter.None,
                runningTime = Time(
                    hour = 10,
                    minute = 10,
                    second = 10
                ),
                locate = Locate(
                    address = "Heaven",
                    latitude = Random.nextDouble(),
                    longitude = Random.nextDouble()
                ),
                distance = Random.nextFloat(),
                meetingDate = Date(),
                message = " ".repeat(20),
                bookmarked = Random.nextBoolean(),
                attendance = Random.nextBoolean(),
            )
        ),
        placeholderEnabled = placeholderEnabled,
    )
}
