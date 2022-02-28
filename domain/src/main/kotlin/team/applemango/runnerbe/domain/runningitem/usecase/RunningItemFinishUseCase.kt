/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemFinishUseCase.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class RunningItemFinishUseCase(private val repo: RunningItemRepository) {
    suspend operator fun invoke(
        jwt: String,
        postId: Int,
    ) = runCatching {
        repo.finish(
            jwt = jwt,
            postId = postId
        )
    }
}