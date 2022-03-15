/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 28. 오후 11:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.repository

import androidx.room.RoomDatabase
import team.applemango.runnerbe.data.common.isSuccessNonNull
import team.applemango.runnerbe.data.common.toBaseResult
import team.applemango.runnerbe.data.common.toJobChangeResult
import team.applemango.runnerbe.data.common.toNicknameChangeResult
import team.applemango.runnerbe.data.runningitem.constant.NotYetVerifyCode
import team.applemango.runnerbe.data.runningitem.constant.SuccessCode
import team.applemango.runnerbe.data.user.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.userApi
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.user.model.MyPageInformation
import team.applemango.runnerbe.domain.user.model.wrapper.JobWrapper
import team.applemango.runnerbe.domain.user.model.wrapper.NicknameWrapper
import team.applemango.runnerbe.domain.user.model.wrapper.ProfileImageUrlWrapper
import team.applemango.runnerbe.domain.user.repository.UserRepository

class UserRepositoryImpl(
    private val database: RoomDatabase,
) : UserRepository {
    override suspend fun setNickname(
        jwt: String,
        userId: Int,
        nickName: NicknameWrapper,
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
    ): BaseResult {
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
        ).toBaseResult()
    }

    override suspend fun loadBookmarkItems(
        jwt: String,
        userId: Int,
    ): List<RunningItem> {
        val request = userApi.loadBookmarkItems(
            jwt = jwt,
            userId = userId,
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.loadBookmarkItems",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode)
            }
        ).toDomain()
    }

    override suspend fun updateProfileImage(
        jwt: String,
        userId: Int,
        profileImageUrl: ProfileImageUrlWrapper,
    ): BaseResult {
        val request = userApi.updateProfileImage(
            jwt = jwt,
            userId = userId,
            profileImageUrl = profileImageUrl
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.updateProfileImage",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode)
            }
        ).toBaseResult()
    }

    override suspend fun changeJob(
        jwt: String,
        userId: Int,
        jobCode: JobWrapper,
    ): BaseResult {
        val request = userApi.changeJob(
            jwt = jwt,
            userId = userId,
            jobCode = jobCode
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.changeJob",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode, 2078)
            }
        ).toJobChangeResult()
    }

    override suspend fun loadMyPage(
        jwt: String,
        userId: Int,
    ): MyPageInformation {
        val request = userApi.loadMyPage(
            jwt = jwt,
            userId = userId,
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.loadMyPage",
            resultVerifyBuilder = { body ->
                body.code == SuccessCode
            }
        ).toDomain()
    }

    override suspend fun attendanceCheck(
        jwt: String,
        postId: Int,
        userId: Int,
    ): Boolean {
        val request = userApi.attendanceCheck(
            jwt = jwt,
            postId = postId,
            userId = userId,
        )
        return request.requireSuccessfulBody(
            requestName = "userApi.attendanceCheck",
            resultVerifyBuilder = { body ->
                body.code == SuccessCode
            }
        ).isSuccessNonNull
    }
}
