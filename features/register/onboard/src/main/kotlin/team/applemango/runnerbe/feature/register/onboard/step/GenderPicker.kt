/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GenderPicker.kt] created by Ji Sungbin on 22. 2. 10. 오전 2:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.register.onboard.constant.Gender
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.compose.extension.collectWithLifecycleRememberOnLaunchedEffect
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.dataStore

@Composable
internal fun GenderPicker(genderSelectChanged: (isSelected: Boolean) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var genderSelectState by remember { mutableStateOf<Gender?>(null) }

    context.dataStore.data.collectWithLifecycleRememberOnLaunchedEffect { preferences ->
        preferences[DataStoreKey.Onboard.Gender]?.let { restoreGenderString ->
            genderSelectChanged(true)
            genderSelectState = Gender.values().first { it.string == restoreGenderString }
        } ?: genderSelectChanged(false)
        cancel("onboard restore execute must be once.")
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        items(Gender.values()) { gender ->
            ToggleButton(
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
