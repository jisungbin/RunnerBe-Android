/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteViewModel.kt] created by Ji Sungbin on 22. 3. 18. 오전 6:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.model.common.DefaultLocate
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.usecase.WriteRunningItemUseCase
import team.applemango.runnerbe.feature.home.write.model.DefaultRunningTime
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.model.RunningTime
import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState
import team.applemango.runnerbe.shared.base.BaseViewModel
import team.applemango.runnerbe.shared.domain.constant.EmptyString

@HiltViewModel
internal class RunningItemWriteViewModel @Inject constructor(
    private val writeRunningItemUseCase: WriteRunningItemUseCase,
) : BaseViewModel(), ContainerHost<RunningItemWriteState, Nothing> {

    override val container = container<RunningItemWriteState, Nothing>(RunningItemWriteState.None)

    var locate = DefaultLocate
        private set

    var runningItemType = RunningItemType.Before
        private set

    var title = EmptyString
        private set

    var runningDate = RunningDate()
        private set

    var runningTime = DefaultRunningTime
        private set

    fun updateAddress(address: Locate) {
        this.locate = address
    }

    fun updateRunningItemType(runningItemType: RunningItemType) {
        this.runningItemType = runningItemType
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateRunningDate(runningDate: RunningDate) {
        this.runningDate = runningDate
    }

    fun updateRunningTime(runningTime: RunningTime) {
        this.runningTime = runningTime
    }
}
