/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItem.kt] created by Ji Sungbin on 22. 3. 2. 오후 4:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.format

private data class DetailItem(
    @DrawableRes val icon: Int,
    val text: String,
)

private const val MeetingDateFormat = "M/d (E) a K:mm" // 3/31 (금) AM 6:00
private const val RunningTimeFormat = ""

@Composable
internal fun RunningItem(
    item: RunningItem,
    bookmarkState: Boolean,
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
                    "${gender.string}만 · $ageFilter"
                    // ageFilter has override toString method with ${min-max}
                }
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color = ColorAsset.G5_5)
            .padding(16.dp)
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
                CoilImage(
                    modifier = Modifier,
                    imageModel = item.ownerProfileImageUrl
                )
                Text(
                    text = item.title,
                    style = Typography.EngBody12M
                )
            }
            Icon( // 북마크
                modifier = Modifier.clickable {
                    requestToggleBookmarkState()
                },
                painter = painterResource(
                    when (bookmarkState) {
                        true -> R.drawable.ic_round_bookmark_24
                        else -> R.drawable.ic_outlined_bookmark_24
                    }
                ),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier,
            text = item.title
        ) // 2 (제목)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            detailItems.chunked(2).forEach { items ->
                items.forEach { item ->
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Text(
                            modifier = Modifier,
                            text = item.text
                        )
                    }
                }
            }
        }
    }
}
