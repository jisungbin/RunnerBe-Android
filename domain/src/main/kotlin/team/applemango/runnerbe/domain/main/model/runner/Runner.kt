/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Runner.kt] created by Ji Sungbin on 22. 2. 25. 오전 3:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model.runner

import team.applemango.runnerbe.domain.main.constant.load.GenderFilter
import team.applemango.runnerbe.domain.common.Job

data class Runner(
    val gender: GenderFilter,
    val nickname: String,
    val job: Job,
    val userId: Int? = null,
    val profileImageUrl: String? = null,
    val age: String? = null,
    val diligence: String? = null,
)
