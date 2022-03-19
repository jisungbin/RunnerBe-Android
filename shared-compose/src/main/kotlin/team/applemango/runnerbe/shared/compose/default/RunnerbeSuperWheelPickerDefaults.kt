/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeSuperWheelPickerDefaults.kt] created by Ji Sungbin on 22. 3. 19. 오전 6:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.default

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontTypeface
import team.applemango.runnerbe.shared.domain.unit.em
import team.applemango.runnerbe.shared.domain.unit.px
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerColors
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerTextStyle

object RunnerbeSuperWheelPickerDefaults {
    fun colors() = SuperWheelPickerColors(
        selectedTextColor = ColorAsset.Primary,
        unselectedTextColor = ColorAsset.G4
    )

    @Composable
    fun textStyle(): SuperWheelPickerTextStyle {
        val context = LocalContext.current
        return SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.regular(context),
            textSize = 24.px,
            letterSpacing = (-0.24).em
        )
    }
}
