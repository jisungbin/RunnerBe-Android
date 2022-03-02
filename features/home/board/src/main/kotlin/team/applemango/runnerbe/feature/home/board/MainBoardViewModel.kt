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
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemsUseCase
import team.applemango.runnerbe.domain.user.usecase.UpdateBookmarkItemUseCase
import team.applemango.runnerbe.shared.base.BaseViewModel

class MainBoardViewModel @AssistedInject constructor(
    private val updateBookmarkItemUseCase: UpdateBookmarkItemUseCase,
    private val loadRunningItemsUseCase: LoadRunningItemsUseCase,
    @Assisted private val userToken: UserToken,
) : BaseViewModel() {

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

    fun updateBookmarkState(bookmarked: Boolean) = viewModelScope.launch {
        updateBookmarkItemUseCase(
            jwt = "",
            postId = 0,
            userId = 0,
            bookmarked = bookmarked
        )
    }
}
