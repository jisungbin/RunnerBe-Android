/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardViewModel.kt] created by Ji Sungbin on 22. 3. 2. 오후 8:03
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardViewModel.kt] created by Ji Sungbin on 22. 3. 2. 오후 6:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

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
import team.applemango.runnerbe.domain.user.usecase.UpdateBookmarkItemUseCase
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState
import team.applemango.runnerbe.shared.base.BaseViewModel

@HiltViewModel
internal class MainBoardViewModel @Inject constructor(
    private val updateBookmarkItemUseCase: UpdateBookmarkItemUseCase,
    private val loadRunningItemsUseCase: LoadRunningItemsUseCase,
) : BaseViewModel(), ContainerHost<MainBoardState, Nothing> {

    override val container = container<MainBoardState, Nothing>(MainBoardState.None)

    fun loadRunningItems(
        itemType: RunningItemType,
        includeEndItems: Boolean,
        itemFilter: RunningItemFilter,
        distanceFilter: DistanceFilter,
        genderFilter: Gender,
        ageFilter: AgeFilter,
        jobFilter: JobFilter,
        locate: Locate,
        keywordFilter: KeywordFilter,
    ) = intent {
        loadRunningItemsUseCase(
            itemType = itemType,
            includeEndItems = includeEndItems,
            itemFilter = itemFilter,
            distanceFilter = distanceFilter,
            genderFilter = genderFilter,
            ageFilter = ageFilter,
            jobFilter = jobFilter,
            locate = locate,
            keywordFilter = keywordFilter
        ).onSuccess { items ->
            if (items.isNotEmpty()) {
                MainBoardDataStore.updateRunningItems(items)
            } else {
                reduce {
                    MainBoardState.RunningItemEmpty
                }
            }
        }.onFailure { exception ->
            emitException(exception)
        }
    }
}
