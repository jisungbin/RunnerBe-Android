/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteScreen.kt] created by Ji Sungbin on 22. 3. 18. 오전 6:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.RunningItemWriteViewModel
import team.applemango.runnerbe.feature.home.write.component.step.RunningItemWriteLevelOne
import team.applemango.runnerbe.feature.home.write.component.step.RunningItemWriteLevelTwo
import team.applemango.runnerbe.feature.home.write.constant.WritingLevel
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.compose.component.RunningItemTypeToggleBar
import team.applemango.runnerbe.shared.compose.component.TopBar
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.extension.activityViewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.theme.animatedColorState
import team.applemango.runnerbe.shared.domain.extension.defaultCatch
import team.applemango.runnerbe.shared.domain.extension.runIf

@OptIn(LocalActivityUsageApi::class) // activityViewModel()
@Composable
internal fun RunningItemWrite(
    modifier: Modifier = Modifier,
    vm: RunningItemWriteViewModel = activityViewModel(),
    restoreLastData: Boolean
) {
    val activity = LocalActivity.current
    val context = activity.applicationContext
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    var selectedRunningItemTypeState by remember { mutableStateOf(RunningItemType.Before) }
    var fieldsAllInputState by remember { mutableStateOf(false) }
    var writingLevelState by remember { mutableStateOf(WritingLevel.One) }

    LaunchedEffect(restoreLastData) {
        if (restoreLastData) {
            val preference = context
                .dataStore
                .data
                .defaultCatch(action = vm::emitException)
                .first()

            preference[DataStoreKey.Write.ItemType]?.let { itemTypeCode ->
                selectedRunningItemTypeState = RunningItemType.values().first {
                    it.code == itemTypeCode
                }
            }
        }
    }

    Column(modifier = modifier) {
        TopBar(
            startContent = {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            activity.finish()
                        },
                    painter = painterResource(R.drawable.ic_round_arrow_left_24),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            centerContent = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.runningitemwrite_title),
                    style = Typography.Body16R.copy(color = ColorAsset.G3)
                )
            },
            endContent = {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .runIf(fieldsAllInputState) {
                            clickable {
                                focusManager.clearFocus()
                                when (writingLevelState) {
                                    WritingLevel.One -> { // 다음 단계
                                        writingLevelState = WritingLevel.Two
                                    }
                                    WritingLevel.Two -> { // 등록
                                        vm.writeRunningItem()
                                    }
                                }
                            }
                        },
                    text = stringResource(
                        when (writingLevelState) {
                            WritingLevel.One -> R.string.runningitemwrite_button_next
                            WritingLevel.Two -> R.string.runningitemwrite_button_publish
                        }
                    ),
                    style = Typography.Body16R.copy(
                        color = animatedColorState(
                            target = true,
                            selectState = fieldsAllInputState,
                            defaultColor = ColorAsset.G4,
                            selectedColor = ColorAsset.Primary
                        )
                    )
                )
            }
        )
        RunningItemTypeToggleBar(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            selectedItemState = selectedRunningItemTypeState,
            onTabClick = { type ->
                selectedRunningItemTypeState = type
                vm.itemType = type
                coroutineScope.launch {
                    context.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Write.ItemType] = type.code
                    }
                }
            }
        )
        Crossfade(
            modifier = Modifier.padding(top = 12.dp),
            targetState = writingLevelState
        ) { level ->
            when (level) {
                WritingLevel.One -> {
                    RunningItemWriteLevelOne(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                        /*.verticalScroll(rememberScrollState())*/, // 지도 세로 스크롤이 안됨
                        runningItemType = selectedRunningItemTypeState,
                        restoreLastData = restoreLastData,
                        inputStateChanged = { isInputted ->
                            fieldsAllInputState = isInputted
                        }
                    )
                }
                WritingLevel.Two -> {
                    RunningItemWriteLevelTwo(
                        modifier = Modifier // 내부에 구분선 있어서 광역 패딩 X
                            .fillMaxSize()
                            .imePadding() // include TextField at bottom
                            .verticalScroll(rememberScrollState()),
                        restoreLastData = restoreLastData
                    )
                }
            }
        }
    }
}
