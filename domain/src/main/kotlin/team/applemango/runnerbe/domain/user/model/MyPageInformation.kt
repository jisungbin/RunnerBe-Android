/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MyPageInformation.kt] created by Ji Sungbin on 22. 3. 1. 오후 2:47
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.model

import team.applemango.runnerbe.domain.runningitem.model.runner.Runner
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem

data class MyPageInformation(
    val me: Runner,
    val ownRunningItems: List<RunningItem>,
    val joinRunnings: List<RunningItem>,
)
