/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BookmarkListRemoteDataSource.kt] created by Ji Sungbin on 22. 2. 28. 오후 4:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.datasource.bookmark

import team.applemango.runnerbe.data.common.toBaseResult
import team.applemango.runnerbe.data.runningitem.constant.NotYetVerifyCode
import team.applemango.runnerbe.data.runningitem.constant.SuccessCode
import team.applemango.runnerbe.data.user.datasource.bookmark.base.BookmarkListBaseDataSource
import team.applemango.runnerbe.data.user.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.userApi
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem

class BookmarkListRemoteDataSource : BookmarkListBaseDataSource {
    override suspend fun updateBookmarkItem(
        jwt: String,
        postId: Int,
        userId: Int,
        whetherAdd: String,
        unregisterUser: Boolean,
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
        unregisterUser: Boolean,
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
}
