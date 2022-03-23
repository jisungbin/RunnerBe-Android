/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardDetail.kt] created by Ji Sungbin on 22. 3. 22. 오후 10:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.feature.home.detail.R
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.compose.component.TopBar
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi

@OptIn(LocalActivityUsageApi::class) // LocalActivity.current
@Composable
internal fun BoardDetail(
    modifier: Modifier = Modifier,
    item: RunningItemInformation,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current

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
                text = item.item.runningType.toString(),
            )
        },
        endContent = {
        }
    )
}
