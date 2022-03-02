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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.common.BaseResult
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
import team.applemango.runnerbe.feature.home.board.mvi.UpdateRunningItemSideEffect
import team.applemango.runnerbe.shared.base.BaseViewModel
import team.applemango.runnerbe.shared.domain.unknownResultMessage

/**
 * TODO: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/52
 */
internal class MainBoardViewModel @AssistedInject constructor(
    private val updateBookmarkItemUseCase: UpdateBookmarkItemUseCase,
    private val loadRunningItemsUseCase: LoadRunningItemsUseCase,
    @Assisted private val userToken: UserToken,
) : BaseViewModel(), ContainerHost<MainBoardState, UpdateRunningItemSideEffect> {

    override val container =
        container<MainBoardState, UpdateRunningItemSideEffect>(MainBoardState.None)

    @AssistedFactory
    interface UserTokenAssistedFactory {
        fun inject(userToken: UserToken): MainBoardViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: UserTokenAssistedFactory,
            userToken: UserToken,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.inject(userToken) as T
            }
        }
    }

    fun updateBookmarkState(itemId: Int, bookmarked: Boolean) = intent {
        if (userToken.jwt == null || userToken.userId == null) {
            reduce {
                MainBoardState.NonRegisterUser
            }
            return@intent
        }
        // Smart cast to 'String' is impossible,
        // because 'userToken.jwt' is a public API property declared in different module
        updateBookmarkItemUseCase(
            jwt = userToken.jwt!!,
            postId = itemId,
            userId = userToken.userId!!,
            bookmarked = bookmarked
        ).onSuccess { result ->
            val newState = when (result) {
                BaseResult.Success -> MainBoardState.None
                BaseResult.NotYetVerify -> MainBoardState.NonRegisterUser
                // needs else block, because BaseResult is interface.
                else -> throw IllegalStateException(unknownResultMessage(result))
            }
            reduce {
                newState
            }
        }.onFailure { exception ->
            emitException(exception)
            reduce {
                MainBoardState.None
            }
        }
    }

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
            postSideEffect(UpdateRunningItemSideEffect(items))
        }.onFailure { exception ->
            emitException(exception)
        }
    }
}
