/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemRequestJoinUseCase.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class RunningItemRequestJoinUseCase(private val repo: RunningItemRepository) {
    suspend operator fun invoke(
        jwt: String,
        postId: Int,
        userId: Int,
    ) = runCatching {
        repo.requestJoin(
            jwt = jwt,
            postId = postId,
            userId = userId
        )
    }
}