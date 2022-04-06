/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemScreen.kt] created by Ji Sungbin on 22. 3. 2. 오후 4:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.model.common.Time
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.shared.compose.component.RunnerbeCoil
import team.applemango.runnerbe.shared.compose.default.RunnerbePlaceholderDefaults
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.format
import java.util.Date
import kotlin.random.Random

@Immutable
private data class DetailItem(
    @DrawableRes val icon: Int,
    val text: String,
)

// format constants 랑 다름
private const val MeetingDateFormat = "M/d (E) a K:mm" // 3/31 (금) AM 6:00

@Composable
internal fun RunningItemScreen(
    modifier: Modifier = Modifier,
    item: RunningItem,
    placeholderEnabled: Boolean = false,
    requestToggleBookmarkState: () -> Unit,
) {
    val detailItems = remember(item) {
        listOf(
            DetailItem(
                icon = R.drawable.ic_outlined_scheduled_24,
                text = item.meetingDate.format(MeetingDateFormat)
            ),
            DetailItem(
                icon = R.drawable.ic_outlined_place_24,
                text = item.locate.address
            ),
            DetailItem(
                icon = R.drawable.ic_outlined_time_24,
                text = with(item.runningTime) {
                    "${hour}시간 ${minute}분"
                }
            ),
            DetailItem(
                icon = R.drawable.ic_round_people_24,
                text = with(item) {
                    var genderString = gender.string
                    if (gender != Gender.All) {
                        genderString += "만"
                    }
                    "$genderString · $ageFilter"
                    // ageFilter has override toString method with ${min-max}
                }
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color = ColorAsset.G5_5)
    ) {
        if (item.finish) {
            Box( // TODO: 디자인 변경
                modifier = Modifier
                    .matchParentSize()
                    .background(color = ColorAsset.G5_5)
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mainboard_runningitem_finished),
                    style = Typography.Body14R.copy(color = ColorAsset.G3_5)
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = ColorAsset.G5_5)
                .padding(
                    top = 16.dp,
                    bottom = 24.dp
                )
                .padding(horizontal = 16.dp)
                .zIndex(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) { // 1 (프사, 닉네임 + 북마크)
                Row( // 프사, 닉네임
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RunnerbeCoil(
                        modifier = Modifier
                            .size(15.dp)
                            .clip(CircleShape)
                            .placeholder(
                                visible = placeholderEnabled,
                                color = RunnerbePlaceholderDefaults.BaseColor,
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                )
                            ),
                        src = item.ownerProfileImageUrl
                    )
                    Text(
                        modifier = Modifier.placeholder(
                            visible = placeholderEnabled,
                            color = RunnerbePlaceholderDefaults.BaseColor,
                            highlight = PlaceholderHighlight.fade(
                                highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                            )
                        ),
                        text = item.ownerNickName,
                        style = Typography.Caption10R.copy(color = ColorAsset.G3_5)
                    )
                }
                Icon( // 북마크
                    modifier = Modifier
                        .placeholder(
                            visible = placeholderEnabled,
                            color = RunnerbePlaceholderDefaults.BaseColor,
                            highlight = PlaceholderHighlight.fade(
                                highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                            )
                        )
                        .noRippleClickable {
                            requestToggleBookmarkState()
                        },
                    painter = painterResource(
                        when (item.bookmarked) {
                            true -> R.drawable.ic_round_bookmark_24
                            else -> R.drawable.ic_outlined_bookmark_24
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Text(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 12.dp
                    )
                    .placeholder(
                        visible = placeholderEnabled,
                        color = RunnerbePlaceholderDefaults.BaseColor,
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                        )
                    ),
                text = item.title,
                style = Typography.Title20R.copy(color = ColorAsset.G3)
            ) // 2 (제목)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(space = 4.dp)
            ) {
                detailItems.chunked(2).forEach { items ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        items.forEach { item ->
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .placeholder(
                                            visible = placeholderEnabled,
                                            color = RunnerbePlaceholderDefaults.BaseColor,
                                            highlight = PlaceholderHighlight.fade(
                                                highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                            )
                                        ),
                                    painter = painterResource(item.icon),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 6.dp)
                                        .placeholder(
                                            visible = placeholderEnabled,
                                            color = RunnerbePlaceholderDefaults.BaseColor,
                                            highlight = PlaceholderHighlight.fade(
                                                highlightColor = RunnerbePlaceholderDefaults.HighlightColor
                                            )
                                        ),
                                    text = item.text,
                                    style = Typography.Body12M.copy(color = ColorAsset.G2)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Dummy Composable for Placeholder
@Composable
internal fun RunningItemScreenDummy(
    modifier: Modifier = Modifier,
    placeholderEnabled: Boolean = true,
) {
    RunningItemScreen(
        modifier = modifier,
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
        ),
        placeholderEnabled = placeholderEnabled,
        requestToggleBookmarkState = {}
    )
}
