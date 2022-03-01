/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SetNicknameUseCase.kt] created by Ji Sungbin on 22. 3. 1. 오전 12:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.usecase

import team.applemango.runnerbe.domain.user.model.wrapper.NicknameWrapper
import team.applemango.runnerbe.domain.user.repository.UserRepository

class SetNicknameUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(
        jwt: String,
        userId: Int,
        nickname: String,
    ) = runCatching {
        repo.setNickname(
            jwt = jwt,
            userId = userId,
            nickName = NicknameWrapper(nickname)
        )
    }
}