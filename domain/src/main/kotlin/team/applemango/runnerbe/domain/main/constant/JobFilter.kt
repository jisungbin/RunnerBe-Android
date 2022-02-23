/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [JobFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:46
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.constant

private typealias JobConstant = team.applemango.runnerbe.domain.register.login.constant.Job

/**
 * 직군 필터
 *
 * @property None 모든 직군
 * @property Job [Job.job] 에 해당하는 직군을 모집하는 아이템만 조회
 */
sealed class JobFilter(val code: String) {
    object None : JobFilter("N")
    data class Job(val job: JobConstant) : JobFilter(job.code)
}
