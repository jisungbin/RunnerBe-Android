/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningDate.kt] created by Ji Sungbin on 22. 3. 18. 오전 8:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.model

import team.applemango.runnerbe.feature.home.write.constant.TimeType

data class RunningDate(
    val dateString: String,
    val timeType: TimeType,
    val hour: Int,
    val minute: Int,
)
