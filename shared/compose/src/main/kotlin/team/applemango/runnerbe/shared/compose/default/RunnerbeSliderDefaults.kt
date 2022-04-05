/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeSliderDefaults.kt] created by Ji Sungbin on 22. 4. 5. 오후 10:22
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.default

import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

object RunnerbeSliderDefaults {
    @Composable
    fun colors() = SliderDefaults.colors(
        thumbColor = ColorAsset.Primary,
        disabledThumbColor = ColorAsset.G4,
        activeTrackColor = ColorAsset.PrimaryDark,
        inactiveTrackColor = ColorAsset.G5_5,
        disabledActiveTrackColor = ColorAsset.G4_5,
        disabledInactiveTrackColor = ColorAsset.G5_5,
        activeTickColor = Color.Transparent,
        inactiveTickColor = ColorAsset.G6,
        disabledActiveTickColor = Color.Transparent,
        disabledInactiveTickColor = ColorAsset.G6,
    )
}
