/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UpdateRunningItem.kt] created by Ji Sungbin on 22. 3. 2. 오후 8:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.mvi

import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem

internal sealed class MainBoardSideEffect {
    data class UpdateRunningItem(val items: List<RunningItem>) : MainBoardSideEffect()
}
