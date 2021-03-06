/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoadRunningItemsUseCase.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.filter.KeywordFilter
import team.applemango.runnerbe.domain.runningitem.filter.RunningItemFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository

class LoadRunningItemsUseCase(private val repo: RunningItemRepository) {
    suspend operator fun invoke(
        itemType: RunningItemType,
        includeEndItems: Boolean,
        itemFilter: RunningItemFilter,
        distanceFilter: DistanceFilter,
        genderFilter: Gender,
        ageFilter: AgeFilter,
        jobFilter: JobFilter,
        locate: Locate,
        keywordFilter: KeywordFilter,
    ) = runCatching {
        repo.loadItems(
            itemType = itemType.code,
            includeEndItems = when (includeEndItems) {
                true -> "Y"
                else -> "N"
            },
            itemFilter = itemFilter.code,
            distance = distanceFilter.value,
            gender = genderFilter.code,
            minAge = ageFilter.getCode { min },
            maxAge = ageFilter.getCode { max },
            job = jobFilter.code,
            latitude = locate.latitude.toFloat(),
            longitude = locate.longitude.toFloat(),
            keyword = keywordFilter.code,
        )
    }
}
