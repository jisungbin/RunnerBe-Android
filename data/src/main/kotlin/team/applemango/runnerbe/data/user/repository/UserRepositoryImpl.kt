/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 28. 오후 11:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.repository

import team.applemango.runnerbe.data.common.isSuccessNonNull
import team.applemango.runnerbe.data.runningitem.constant.NotYetVerifyCode
import team.applemango.runnerbe.data.runningitem.constant.SuccessCode
import team.applemango.runnerbe.data.user.mapper.toNicknameChangeResult
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.userApi
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.user.model.Nickname
import team.applemango.runnerbe.domain.user.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun setNickname(
        jwt: String,
        userId: Int,
        nickName: Nickname,
    ): BaseResult {
        val request = userApi.setNickname(
            jwt = jwt,
            userId = userId,
            nickname = nickName
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.setNickname",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode, 3004, 3005)
            }
        ).toNicknameChangeResult()
    }

    override suspend fun updateBookmarkItem(
        jwt: String,
        postId: Int,
        userId: Int,
        whetherAdd: String,
    ): Boolean {
        val request = userApi.updateBookmarkItem(
            jwt = jwt,
            postId = postId,
            userId = userId,
            whetherAdd = whetherAdd
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.updateBookmarkItem",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode)
            }
        ).isSuccessNonNull
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