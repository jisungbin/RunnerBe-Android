/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GenderPicker.kt] created by Ji Sungbin on 22. 2. 10. 오전 2:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.util.collectWithLifecycleRememberOnLaunchedEffect
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.dataStore

private val GenderShape = RoundedCornerShape(20.dp)

private enum class Gender(val string: String) {
    Male("남성"), Female("여성")
}

@Composable
internal fun GenderPicker(genderSelectChanged: (isSelected: Boolean) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedGender by remember { mutableStateOf<Gender?>(null) }

    @Composable
    fun backgroundColor(gender: Gender) = animateColorAsState(
        if (gender == selectedGender) ColorAsset.Primary else Color.Transparent
    ).value

    @Composable
    fun borderColor(gender: Gender) = animateColorAsState(
        if (gender == selectedGender) ColorAsset.Primary else ColorAsset.G4
    ).value

    @Composable
    fun textColor(gender: Gender) = animateColorAsState(
        if (gender == selectedGender) Color.Black else ColorAsset.G4
    ).value

    context.dataStore.data.collectWithLifecycleRememberOnLaunchedEffect { preferences ->
        preferences[DataStoreKey.Onboard.Gender]?.let { restoreGenderString ->
            genderSelectChanged(true)
            selectedGender = Gender.values().first { it.string == restoreGenderString }
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
            Button(
                onClick = {
                    selectedGender = gender
                    genderSelectChanged(true)
                    coroutineScope.launch {
                        context.dataStore.edit { preferences ->
                            preferences[DataStoreKey.Onboard.Gender] = gender.string
                        }
                    }
                },
                shape = GenderShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor(gender)),
                border = BorderStroke(width = 1.dp, color = borderColor(gender))
            ) {
                Text(
                    text = gender.string,
                    style = Typography.Body14R.copy(color = textColor(gender))
                )
            }
        }
    }
}
