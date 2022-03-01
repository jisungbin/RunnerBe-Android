/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemDeleteUseCase.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class RunningItemDeleteUseCase(private val repo: RunningItemRepository) {
    suspend operator fun invoke(
        jwt: String,
        postId: Int,
        userId: Int,
    ) = runCatching {
        repo.delete(
            jwt = jwt,
            postId = postId,
            userId = userId
        )
    }
}
