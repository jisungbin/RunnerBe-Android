/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AttendanceCheckUseCase.kt] created by Ji Sungbin on 22. 3. 1. 오후 3:37
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.usecase

import team.applemango.runnerbe.domain.user.repository.UserRepository

class AttendanceCheckUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(
        jwt: String,
        postId: Int,
        userId: Int,
    ) = runCatching {
        repo.attendanceCheck(
            jwt = jwt,
            postId = postId,
            userId = userId
        )
    }
}
