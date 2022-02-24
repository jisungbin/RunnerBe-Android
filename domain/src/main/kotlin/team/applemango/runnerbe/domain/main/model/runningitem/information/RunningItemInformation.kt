/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 22. 2. 24. 오후 10:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model.runningitem.information

import team.applemango.runnerbe.domain.main.model.runner.Runner
import team.applemango.runnerbe.domain.main.model.runningitem.RunningItem

data class RunningItemInformation(
    val item: RunningItem,
    val joinRunners: List<Runner>,
    val waitingRunners: List<Runner>,
)
