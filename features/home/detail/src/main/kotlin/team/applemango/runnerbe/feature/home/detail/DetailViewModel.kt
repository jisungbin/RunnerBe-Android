/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DetailViewModel.kt] created by Ji Sungbin on 22. 3. 22. 오후 10:54
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemDetailUseCase
import team.applemango.runnerbe.feature.home.detail.mvi.DetailLoadState
import team.applemango.runnerbe.shared.android.base.BaseViewModel
import team.applemango.runnerbe.shared.android.datastore.Me
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val loadRunningItemDetailUseCase: LoadRunningItemDetailUseCase,
) : BaseViewModel(), ContainerHost<DetailLoadState, Nothing> {

    override val container = container<DetailLoadState, Nothing>(DetailLoadState.Loading)

    fun loadItemDetail(postId: Int) = intent {
        val jwt = checkNotNull(Me.token.jwt) {
            "User must be registered."
        }
        val userId = checkNotNull(Me.token.userId) {
            "User must be registered."
        }
        loadRunningItemDetailUseCase(
            jwt = jwt,
            postId = postId,
            userId = userId,
        ).onSuccess { detail ->
            reduce {
                DetailLoadState.Loaded(detail)
            }
        }.onFailure { exception ->
            emitException(exception)
        }
    }
}
