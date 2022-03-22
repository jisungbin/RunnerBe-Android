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
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemDetailUseCase
import team.applemango.runnerbe.feature.home.detail.mvi.DetailLoadState
import team.applemango.runnerbe.feature.home.detail.mvi.DetailSideEffect
import team.applemango.runnerbe.shared.android.base.BaseViewModel

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    @Inject private val loadRunningItemDetailUseCase: LoadRunningItemDetailUseCase,
) : BaseViewModel(), ContainerHost<DetailLoadState, DetailSideEffect> {

    override val container = container<DetailLoadState, DetailSideEffect>(DetailLoadState.Load)

    fun loadItemDetail(
        jwt: String,
        postId: Int,
        userId: Int,
    ) = intent {
        loadRunningItemDetailUseCase(
            jwt = jwt,
            postId = postId,
            userId = userId
        ).onSuccess { detail ->
            if (detail == null) {
                reduce {
                    DetailLoadState.NotVerified
                }
            } else {
                reduce {
                    DetailLoadState.Done
                }
                postSideEffect(DetailSideEffect.LoadDone(detail))
            }
        }.onFailure { exception ->
            emitException(exception)
        }
    }
}
