/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemsState.kt] created by Ji Sungbin on 22. 3. 26. 오후 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.mvi

import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem

/**
 * 러닝 아이템 조회 상태
 */
internal sealed class RunningItemsState {
    object Loading : RunningItemsState()
    object LoadFail : RunningItemsState()
    object Empty : RunningItemsState()
    data class Loaded(val items: List<RunningItem>) : RunningItemsState()
}
