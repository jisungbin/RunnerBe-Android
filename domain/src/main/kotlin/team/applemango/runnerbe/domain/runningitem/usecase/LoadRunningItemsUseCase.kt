/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoadRunningItemsUseCase.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.filter.KeywordFilter
import team.applemango.runnerbe.domain.runningitem.filter.RunningItemFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.repository.RunningItemRepository
import team.applemango.runnerbe.domain.user.repository.UserRepository
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage

class LoadRunningItemsUseCase(
    private val runningItemRepo: RunningItemRepository,
    private val userRepo: UserRepository,
) {
    suspend operator fun invoke(
        userToken: UserToken,
        itemType: RunningItemType,
        includeEndItems: Boolean,
        itemFilter: RunningItemFilter,
        distanceFilter: DistanceFilter,
        genderFilter: Gender,
        ageFilter: AgeFilter,
        jobFilter: JobFilter,
        locate: Locate,
        keywordFilter: KeywordFilter,
    ) = coroutineScope {
        runCatching {
            val runningItemsAsync = async {
                runningItemRepo.loadItems(
                    itemType = itemType.code,
                    includeEndItems = when (includeEndItems) {
                        true -> "Y"
                        else -> "N"
                    },
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
            val bookmarkItemsAsync = async {
                userRepo.loadBookmarkItems(
                    jwt = requireNotNull(userToken.jwt) {
                        requireFieldNullMessage("jwt")
                    },
                    userId = requireNotNull(userToken.userId) {
                        requireFieldNullMessage("userId")
                    }
                )
            }
            val (runningItems, bookmarkItems) = listOf(
                runningItemsAsync,
                bookmarkItemsAsync
            ).awaitAll()
            val bookmarkItemsId = bookmarkItems.map { it.itemId }
            runningItems.map { item ->
                item.copy(bookmarked = bookmarkItemsId.contains(item.itemId))
            }
        }
    }
}
