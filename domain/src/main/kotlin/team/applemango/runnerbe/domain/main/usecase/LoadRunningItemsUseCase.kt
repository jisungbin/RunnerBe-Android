/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoadRunningItemsUseCase.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.usecase

import team.applemango.runnerbe.domain.main.constant.AgeRange
import team.applemango.runnerbe.domain.main.constant.DistanceFilter
import team.applemango.runnerbe.domain.main.constant.GenderFilter
import team.applemango.runnerbe.domain.main.constant.JobFilter
import team.applemango.runnerbe.domain.main.constant.KeywordFilter
import team.applemango.runnerbe.domain.main.constant.RunningItemFilter
import team.applemango.runnerbe.domain.main.constant.RunningItemType
import team.applemango.runnerbe.domain.main.model.Locate
import team.applemango.runnerbe.domain.main.repository.MainRepository

class LoadRunningItemsUseCase(private val repo: MainRepository) {
    suspend operator fun invoke(
        itemType: RunningItemType,
        includeEndItems: Boolean,
        itemFilter: RunningItemFilter,
        distanceFilter: DistanceFilter,
        genderFilter: GenderFilter,
        ageRange: AgeRange,
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
            minAge = ageRange.getCode { min },
            maxAge = ageRange.getCode { max },
            job = jobFilter.code,
            latitude = locate.latitude.toFloat(),
            longitude = locate.longitude.toFloat(),
            keyword = keywordFilter.code
        )
    }
}
