/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoadRunningItemsUseCase.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.usecase

import team.applemango.runnerbe.domain.main.common.RunningItemType
import team.applemango.runnerbe.domain.main.filter.AgeFilter
import team.applemango.runnerbe.domain.main.filter.DistanceFilter
import team.applemango.runnerbe.domain.main.filter.JobFilter
import team.applemango.runnerbe.domain.main.filter.KeywordFilter
import team.applemango.runnerbe.domain.main.filter.RunningItemFilter
import team.applemango.runnerbe.domain.main.model.common.Locate
import team.applemango.runnerbe.domain.main.repository.RunningItemRepository
import team.applemango.runnerbe.domain.constant.Gender

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
        repo.loadRunningItems(
            itemType = itemType.code,
            includeEndItems = includeEndItems,
            itemFilter = itemFilter.code,
            distance = distanceFilter.code,
            gender = genderFilter.code,
            minAge = ageFilter.getCode { min },
            maxAge = ageFilter.getCode { max },
            job = jobFilter.code,
            latitude = locate.latitude.toFloat(),
            longitude = locate.longitude.toFloat(),
            keyword = keywordFilter.code
        )
    }
}
