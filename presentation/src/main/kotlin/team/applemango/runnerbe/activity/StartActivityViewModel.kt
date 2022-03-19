/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [StartActivityViewModel.kt] created by Ji Sungbin on 22. 3. 16. 오전 1:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.filter.KeywordFilter
import team.applemango.runnerbe.domain.runningitem.filter.RunningItemFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemsUseCase
import team.applemango.runnerbe.mvi.StartActivityState
import team.applemango.runnerbe.shared.android.base.BaseViewModel
import team.applemango.runnerbe.shared.domain.constant.EmptyString

@HiltViewModel
internal class StartActivityViewModel @Inject constructor(
    private val loadRunningItemsUseCase: LoadRunningItemsUseCase,
) : BaseViewModel(), ContainerHost<StartActivityState, Nothing> {

    private companion object {
        const val DefaultLocate = .0
    }

    override val container = container<StartActivityState, Nothing>(StartActivityState.None)

    fun loadAllRunningItems() = intent {
        loadRunningItemsUseCase(
            itemType = RunningItemType.Before,
            includeEndItems = false,
            itemFilter = RunningItemFilter.Distance,
            distanceFilter = DistanceFilter.None,
            genderFilter = Gender.All,
            ageFilter = AgeFilter(min = null, max = null),
            jobFilter = JobFilter.None,
            locate = Locate(
                address = EmptyString, latitude = DefaultLocate, longitude = DefaultLocate
            ),
            keywordFilter = KeywordFilter.None,
            useCaching = true
        ).onSuccess {
            reduce {
                StartActivityState.Loaded
            }
        }.onFailure { exception ->
            emitException(exception)
        }
    }
}
