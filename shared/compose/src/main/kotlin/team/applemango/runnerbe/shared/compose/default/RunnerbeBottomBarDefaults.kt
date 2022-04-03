package team.applemango.runnerbe.shared.compose.default

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.shared.compose.component.BottomBarColors
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

object RunnerbeBottomBarDefaults {
    @Stable
    val height = 60.dp

    @Stable
    fun colors() = BottomBarColors(
        background = ColorAsset.G6,
        indicatorBackground = Color.Transparent,
        primary = ColorAsset.Primary
    )
}
