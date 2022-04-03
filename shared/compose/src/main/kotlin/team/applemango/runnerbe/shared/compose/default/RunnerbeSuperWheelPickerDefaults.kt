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
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontTypeface
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerColors
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerTextStyle

object RunnerbeSuperWheelPickerDefaults {
    @Stable
    fun colors() = SuperWheelPickerColors(
        selectedTextColor = ColorAsset.Primary,
        unselectedTextColor = ColorAsset.G4
    )

    @Stable
    @Composable
    fun textStyle(): SuperWheelPickerTextStyle {
        // Resource load 는 Application Context 에서 해도 됨
        // See: https://medium.com/@joaofoltran/what-really-is-context-in-android-2034561fff39
        val context = LocalContext.current.applicationContext
        return SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.medium(context),
            textSize = 50f,
        )
    }
}
