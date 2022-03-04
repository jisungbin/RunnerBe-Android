/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ChangeJobUseCase.kt] created by Ji Sungbin on 22. 3. 1. 오후 3:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.usecase

import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.user.model.wrapper.JobWrapper
import team.applemango.runnerbe.domain.user.repository.UserRepository

class ChangeJobUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(
        jwt: String,
        userId: Int,
        job: Job,
    ) = runCatching {
        repo.changeJob(
            jwt = jwt,
            userId = userId,
            jobCode = JobWrapper(job.name)
        )
    }
}
