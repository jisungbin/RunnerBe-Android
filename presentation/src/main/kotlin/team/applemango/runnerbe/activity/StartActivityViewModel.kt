/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [StartActivityViewModel.kt] created by Ji Sungbin on 22. 3. 16. 오전 1:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.filter.KeywordFilter
import team.applemango.runnerbe.domain.runningitem.filter.RunningItemFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemsUseCase
import team.applemango.runnerbe.shared.base.BaseViewModel
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import javax.inject.Inject

@HiltViewModel
internal class StartActivityViewModel @Inject constructor(
    private val loadRunningItemsUseCase: LoadRunningItemsUseCase,
) : BaseViewModel() {

    private companion object {
        const val DefaultLocate = .0
    }

    fun loadAllRunningItems(done: (items: List<RunningItem>) -> Unit) = viewModelScope.launch {
        loadRunningItemsUseCase(
            itemType = RunningItemType.Before,
            includeEndItems = false,
            itemFilter = RunningItemFilter.Distance,
            distanceFilter = DistanceFilter.None,
            genderFilter = Gender.All,
            ageFilter = AgeFilter(min = null, max = null),
            jobFilter = JobFilter.None,
            locate = Locate(
                address = EmptyString,
                latitude = DefaultLocate,
                longitude = DefaultLocate
            ),
            keywordFilter = KeywordFilter.None
        ).onSuccess(done)
            .onFailure { exception ->
                emitException(exception)
            }
    }
}
