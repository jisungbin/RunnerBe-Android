/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemEditUseCase.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import team.applemango.runnerbe.domain.runningitem.mapper.toData
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBody
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class RunningItemEditUseCase(private val repo: RunningItemRepository) {
    suspend operator fun invoke(
        jwt: String,
        postId: Int,
        userId: Int,
        item: RunningItemApiBody,
    ) = runCatching {
        repo.edit(
            jwt = jwt,
            postId = postId,
            userId = userId,
            item = item.toData()
        )
    }
}
