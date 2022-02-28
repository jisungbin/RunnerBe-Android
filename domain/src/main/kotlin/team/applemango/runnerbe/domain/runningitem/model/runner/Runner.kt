/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Runner.kt] created by Ji Sungbin on 22. 2. 25. 오전 3:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.model.runner

import team.applemango.runnerbe.domain.constant.AgeGroup
import team.applemango.runnerbe.domain.constant.Diligence
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job

data class Runner(
    val id: Int,
    val nickname: String,
    val gender: Gender,
    val job: Job,
    val profileImageUrl: String,
    val ageGroup: AgeGroup,
    val diligence: Diligence,
)
