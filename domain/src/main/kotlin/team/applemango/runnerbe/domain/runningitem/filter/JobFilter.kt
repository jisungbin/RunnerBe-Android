/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [JobFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:46
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.filter

import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter.Create
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter.None

/**
 * 직군 필터
 *
 * @property None 모든 직군
 * @property Create [Create.job] 에 해당하는 직군을 모집하는 아이템만 조회
 */
sealed class JobFilter(val code: String) {
    object None : JobFilter("N")
    data class Create(val job: Job) : JobFilter(job.name)

    fun toJob() = Job.values().first { it.name == code }
}
