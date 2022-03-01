/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UpdateProfileImageUseCase.kt] created by Ji Sungbin on 22. 3. 1. 오후 3:34
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.usecase

import team.applemango.runnerbe.domain.user.model.wrapper.ProfileImageUrlWrapper
import team.applemango.runnerbe.domain.user.repository.UserRepository

class UpdateProfileImageUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(
        jwt: String,
        userId: Int,
        imageUrl: String,
    ) = runCatching {
        repo.updateProfileImage(
            jwt = jwt,
            userId = userId,
            profileImageUrl = ProfileImageUrlWrapper(imageUrl)
        )
    }
}
