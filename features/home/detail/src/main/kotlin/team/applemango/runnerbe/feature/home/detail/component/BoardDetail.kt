/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardDetail.kt] created by Ji Sungbin on 22. 3. 22. 오후 10:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail.component

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.shared.compose.component.TopBar

@Composable
internal fun BoardDetail(
    modifier: Modifier = Modifier,
    item: RunningItemInformation,
) {
    val context = LocalContext.current

    TopBar(
        startContent = {
            Icon(
                painter = ,
                contentDescription = null
            )
        },
        centerContent = {
        },
        endContent = {
        }
    )
}
