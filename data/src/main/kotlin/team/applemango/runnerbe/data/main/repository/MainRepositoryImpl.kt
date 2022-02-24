/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [aaa.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.repository

import team.applemango.runnerbe.data.main.mapper.runningitem.MappingType
import team.applemango.runnerbe.data.main.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.extension.toXAccessTokenHeader
import team.applemango.runnerbe.data.util.mainApi
import team.applemango.runnerbe.domain.main.common.BaseResult
import team.applemango.runnerbe.domain.main.model.common.RunningItem
import team.applemango.runnerbe.domain.main.model.write.RunningItemBodyData
import team.applemango.runnerbe.domain.main.repository.MainRepository

class MainRepositoryImpl : MainRepository {
    override suspend fun loadRunningItems(
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
        val request = mainApi.loadRunningItems(
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
            requestName = "mainApi.loadRunningItems",
            resultVerifyBuilder = { body ->
                body.code == 1000
            }
        ).toDomain(type = MappingType.MainPageApiFields)
    }

    override suspend fun writeRunningItem(
        jwt: String,
        userId: Int,
        item: RunningItemBodyData,
    ): BaseResult {
        val request = mainApi.writeRunningItem(
            jwtHeader = jwt.toXAccessTokenHeader(),
            userId = userId,
            item = item
        )
        return request.requireSuccessfulBody(
            requestName = "mainApi.writeRunningItem",
            resultVerifyBuilder = { body ->
                body.code != null
            }
        ).toDomain()
    }
}
