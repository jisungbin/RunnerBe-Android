/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GenderPicker.kt] created by Ji Sungbin on 22. 2. 10. 오전 2:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.compose.default.RunnerbeToggleButtonDefaults
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

// `vm: OnboardViewModel = activityViewModels()` 안한 이유:
// DFM 는 의존성이 반대로 되서 hilt 를 사용하지 못함
// 따라서 직접 factory 로 인자를 주입해 줘야 함
// 이는 OnboardActivity 에서 해주고 있으므로,
// OnboardActivity 에서 vm 를 가져와야 함
@Composable
internal fun GenderPicker(
    vm: OnboardViewModel,
    genderSelectChanged: (isSelected: Boolean) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    var genderSelectState by remember { mutableStateOf<Gender?>(null) }

    LaunchedEffect(Unit) {
        val preferences = context
            .dataStore
            .data
            .defaultCatch(action = vm::emitException)
            .first()

        preferences[DataStoreKey.Onboard.Gender]?.let { restoreGenderString ->
            genderSelectChanged(true)
            genderSelectState = Gender.values().first { it.string == restoreGenderString }
        } ?: genderSelectChanged(false)
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        items(items = Gender.values().filterNot { it == Gender.All }) { gender ->
            ToggleButton(
                colors = RunnerbeToggleButtonDefaults.colors(),
                target = gender,
                selectState = genderSelectState,
                targetStringBuilder = { gender.string }
            ) {
                genderSelectState = gender
                genderSelectChanged(true)
                coroutineScope.launch {
                    context.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Onboard.Gender] = gender.string
                    }
                }
            }
        }
    }
}
