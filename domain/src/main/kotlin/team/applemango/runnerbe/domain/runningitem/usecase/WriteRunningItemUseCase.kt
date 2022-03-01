/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [WriteRunningItemUseCase.kt] created by Ji Sungbin on 22. 2. 24. 오후 9:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import team.applemango.runnerbe.domain.runningitem.mapper.toData
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBody
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class WriteRunningItemUseCase(private val repo: RunningItemRepository) {
    suspend operator fun invoke(
        jwt: String,
        userId: Int,
        item: RunningItemApiBody,
    ) = runCatching {
        repo.write(
            jwt = jwt,
            userId = userId,
            item = item.toData()
        )
    }
}
