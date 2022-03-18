/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeToggleButtonColors.kt] created by Ji Sungbin on 22. 3. 19. 오전 6:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.default

import androidx.compose.ui.graphics.Color
import team.applemango.runnerbe.shared.compose.component.ToggleButtonColors
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

val RunnerbeToggleButtonColors = ToggleButtonColors(
    backgroundDefaultColor = Color.Transparent,
    backgroundSelectedColor = ColorAsset.Primary,
    borderDefaultColor = ColorAsset.G4,
    borderSelectedColor = ColorAsset.Primary,
)
