/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Runner.kt] created by Ji Sungbin on 22. 2. 25. 오전 3:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model.runner

import team.applemango.runnerbe.domain.runner.Gender
import team.applemango.runnerbe.domain.runner.Job

data class Runner(
    val id: Int,
    val nickname: String,
    val gender: Gender,
    val job: Job,
    val profileImageUrl: String,
    val ageGroup: String,
    val diligence: String,
)
