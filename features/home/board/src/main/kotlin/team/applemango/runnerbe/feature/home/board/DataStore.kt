/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DataStore.kt] created by Ji Sungbin on 22. 3. 16. 오전 1:09
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem

object DataStore {
    var runningItems = emptyList<RunningItem>()
        private set

    fun updateRunningItems(items: List<RunningItem>) {
        runningItems = items
    }
}
