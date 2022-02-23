/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [aaa.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.repository

import team.applemango.runnerbe.data.util.mainApi
import team.applemango.runnerbe.domain.main.model.RunningItem
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
    }
}
