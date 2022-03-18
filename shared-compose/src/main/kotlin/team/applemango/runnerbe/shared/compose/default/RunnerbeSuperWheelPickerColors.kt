package team.applemango.runnerbe.shared.compose.default

import androidx.compose.runtime.Composable
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerColors

val RunnerbeSuperWheelPickerColors
    @Composable get() = SuperWheelPickerColors(
        selectedTextColor = ColorAsset.Primary,
        unselectedTextColor = ColorAsset.G4
    )
