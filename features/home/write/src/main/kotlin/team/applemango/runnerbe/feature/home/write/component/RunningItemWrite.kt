/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWrite.kt] created by Ji Sungbin on 22. 3. 19. 오후 11:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWrite.kt] created by Ji Sungbin on 22. 3. 18. 오전 6:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.RunningItemWriteViewModel
import team.applemango.runnerbe.feature.home.write.component.step.RunningItemWriteLevelOne
import team.applemango.runnerbe.feature.home.write.component.step.RunningItemWriteLevelTwo
import team.applemango.runnerbe.feature.home.write.constant.WritingLevel
import team.applemango.runnerbe.shared.compose.component.RunningItemTypeToggleBar
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState
import team.applemango.runnerbe.shared.domain.extension.runIf

@OptIn(LocalActivityUsageApi::class) // activityViewModel()
@Composable
internal fun RunningItemWrite(
    modifier: Modifier = Modifier,
    vm: RunningItemWriteViewModel = activityViewModel(),
) {
    val activity = LocalActivity.current
    var selectedRunningItemType = remember { RunningItemType.Before }
    val fieldsAllInputState = remember { mutableStateListOf(false, false) }
    var writingLevel by remember { mutableStateOf(WritingLevel.One) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        activity.finish()
                    },
                painter = painterResource(R.drawable.ic_round_left_arrow_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.runningitemwrite_title),
                style = Typography.Body16R.copy(color = ColorAsset.G3)
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .runIf(fieldsAllInputState[writingLevel.index]) {
                        clickable {
                            @Suppress("UNUSED_EXPRESSION") // vm
                            when (writingLevel) {
                                WritingLevel.One -> writingLevel = WritingLevel.Two // 다음 단계
                                WritingLevel.Two -> { // 등록
                                    // TODO
                                    vm
                                }
                            }
                        }
                    },
                text = stringResource(
                    when (writingLevel) {
                        WritingLevel.One -> R.string.runningitemwrite_button_next
                        WritingLevel.Two -> R.string.runningitemwrite_button_publish
                    }
                ),
                style = Typography.Body16R.copy(
                    color = animatedColorState(
                        target = true,
                        selectState = fieldsAllInputState[writingLevel.index],
                        defaultColor = ColorAsset.G4,
                        selectedColor = ColorAsset.Primary
                    )
                )
            )
        }
        RunningItemTypeToggleBar(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            onTabClick = { type ->
                selectedRunningItemType = type
            }
        )
        Crossfade(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            targetState = writingLevel
        ) { level ->
            when (level) {
                WritingLevel.One -> {
                    RunningItemWriteLevelOne(
                        runningItemType = selectedRunningItemType,
                        fieldsAllInputStateChange = { state ->
                            fieldsAllInputState[writingLevel.index] = state
                        }
                    )
                }
                WritingLevel.Two -> {
                    RunningItemWriteLevelTwo(
                        modifier = Modifier.imePadding() // include TextField at bottom
                    )
                }
            }
        }
    }
}
