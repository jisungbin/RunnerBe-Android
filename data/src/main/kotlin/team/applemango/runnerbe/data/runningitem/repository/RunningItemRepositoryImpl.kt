/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [aaa.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.repository

import team.applemango.runnerbe.data.common.toSuccessValueBoolean
import team.applemango.runnerbe.data.runningitem.constant.NotYetVerifyCode
import team.applemango.runnerbe.data.runningitem.constant.SuccessCode
import team.applemango.runnerbe.data.runningitem.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.runningItemApi
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBodyData
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class RunningItemRepositoryImpl : RunningItemRepository {
    override suspend fun write(
        jwt: String,
        userId: Int,
        item: RunningItemApiBodyData,
    ): BaseResult {
        val request = runningItemApi.write(
            jwt = jwt,
            userId = userId,
            item = item
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.writeRunningItem",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode)
            }
        ).toDomain()
    }

    override suspend fun loadItems(
        itemType: String,
        includeEndItems: Boolean,
        itemFilter: String,
        distance: String,
        gender: String,
        minAge: String,
        maxAge: String,
        job: String,
        latitude: Float,
        longitude: Float,
        keyword: String,
    ): List<RunningItem> {
        val request = runningItemApi.loadItems(
            itemType = itemType,
            includeEndItems = includeEndItems,
            itemFilter = itemFilter,
            distance = distance,
            gender = gender,
            minAge = minAge,
            maxAge = maxAge,
            job = job,
            latitude = latitude,
            longitude = longitude,
            keyword = keyword
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.loadRunningItems",
            resultVerifyBuilder = { body ->
                body.code in listOf(SuccessCode, NotYetVerifyCode)
            }
        ).toDomain()
    }

    override suspend fun loadInformation(
        jwt: String,
        postId: Int,
        userId: Int,
    ): RunningItemInformation? {
        val request = runningItemApi.loadInformation(
            jwt = jwt,
            postId = postId,
            userId = userId,
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.getRunningItemInformation",
            resultVerifyBuilder = { body ->
                body.code in 1015..1020 || body.code == NotYetVerifyCode
            }
        ).toDomain()
    }

    override suspend fun finish(jwt: String, postId: Int): Boolean {
        val request = runningItemApi.finish(
            jwt = jwt,
            postId = postId
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.finish",
            checkBodyIsSuccess = false,
            resultVerifyBuilder = { body ->
                body.isSuccess != null
            }
        ).toSuccessValueBoolean()
    }

    override suspend fun edit(
        jwt: String,
        postId: Int,
        userId: Int,
        item: RunningItemApiBodyData,
    ): Boolean {
        val request = runningItemApi.edit(
            jwt = jwt,
            userId = userId,
            postId = postId,
            item = item
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.edit",
            checkBodyIsSuccess = false,
            resultVerifyBuilder = { body ->
                body.isSuccess != null
            }
        ).toSuccessValueBoolean()
    }

    override suspend fun delete(
        jwt: String,
        postId: Int,
        userId: Int,
    ): Boolean {
        val request = runningItemApi.delete(
            jwt = jwt,
            postId = postId,
            userId = userId,
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.delete",
            checkBodyIsSuccess = false,
            resultVerifyBuilder = { body ->
                body.isSuccess != null
            }
        ).toSuccessValueBoolean()
    }

    override suspend fun requestJoin(
        jwt: String,
        postId: Int,
        userId: Int,
    ): BaseResult {
        val request = runningItemApi.requestJoin(
            jwt = jwt,
            postId = postId,
            userId = userId,
        )
        return request.requireSuccessfulBody(
            requestName = "runningItemApi.delete",
            checkBodyIsSuccess = false,
            resultVerifyBuilder = { body ->
                body.isSuccess != null
            }
        )
    }

    override suspend fun joinManage(
        jwt: String,
        postId: Int,
        userId: Int,
        runnerId: String,
        state: Boolean,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun report(
        jwt: String,
        postId: Int,
        userId: Int,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
