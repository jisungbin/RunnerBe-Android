/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeToggleTabBarDefaults.kt] created by Ji Sungbin on 22. 3. 19. 오전 6:25
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.default

import androidx.compose.runtime.Stable
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.shared.compose.component.ToggleTopBarColors
import team.applemango.runnerbe.shared.compose.component.ToggleTopBarItem
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

object RunnerbeToggleTabBarDefaults {
    fun items() = listOf(
        ToggleTopBarItem(id = RunningItemType.Before, text = RunningItemType.Before.toString()),
        ToggleTopBarItem(id = RunningItemType.After, text = RunningItemType.After.toString()),
        ToggleTopBarItem(id = RunningItemType.Off, text = RunningItemType.Off.toString()),
    )

    @Stable
    fun colors() = ToggleTopBarColors(
        baseBackground = ColorAsset.G6,
        activateBackground = ColorAsset.Primary,
        activateText = ColorAsset.G6,
        inactivateText = ColorAsset.G4_5,
    )
}
