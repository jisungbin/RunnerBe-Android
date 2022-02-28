/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 28. 오후 11:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.repository

import team.applemango.runnerbe.data.util.userApi
import team.applemango.runnerbe.domain.user.constant.NicknameChangeResult
import team.applemango.runnerbe.domain.user.model.Nickname
import team.applemango.runnerbe.domain.user.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun setNickname(
        jwt: String,
        userId: Int,
        nickName: Nickname,
    ): NicknameChangeResult {
        val request = userApi.setNickname(
            jwt = jwt,
            userId = userId,
            nickname = nickName
        )
    }

    override suspend fun updateBookmarkItem(
        jwt: String,
        postId: Int,
        userId: Int,
        whetherAdd: String,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loadBookmarkItems() {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfileImage() {
        TODO("Not yet implemented")
    }

    override suspend fun checkJob() {
        TODO("Not yet implemented")
    }

    override suspend fun loadMyPage() {
        TODO("Not yet implemented")
    }

    override suspend fun attendanceCheck() {
        TODO("Not yet implemented")
    }
}