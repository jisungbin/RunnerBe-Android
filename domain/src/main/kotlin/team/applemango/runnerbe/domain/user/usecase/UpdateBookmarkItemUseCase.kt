/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UpdateBookmarkItemUseCase.kt] created by Ji Sungbin on 22. 3. 1. 오전 12:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.usecase

import team.applemango.runnerbe.domain.user.repository.UserRepository

class UpdateBookmarkItemUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(
        jwt: String,
        itemId: Int,
        userId: Int,
        bookmarked: Boolean,
    ) = runCatching {
        repo.updateBookmarkItem(
            jwt = jwt,
            postId = itemId,
            userId = userId,
            whetherAdd = when (bookmarked) {
                true -> "Y"
                else -> "N"
            }
        )
    }
}
