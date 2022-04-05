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
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBody
import team.applemango.runnerbe.domain.runningitem.usecase.WriteRunningItemUseCase
import team.applemango.runnerbe.feature.home.write.model.DefaultRunningTime
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState
import team.applemango.runnerbe.shared.android.base.BaseViewModel
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import javax.inject.Inject

@HiltViewModel
internal class RunningItemWriteViewModel @Inject constructor(
    private val writeRunningItemUseCase: WriteRunningItemUseCase,
) : BaseViewModel(), ContainerHost<RunningItemWriteState, Nothing> {

    override val container = container<RunningItemWriteState, Nothing>(RunningItemWriteState.None)

    var title = EmptyString
    var message = EmptyString
    var gender = Gender.All
    var maxPeopleCount = 0
    var ageFilter = AgeFilter.None
    var itemType = RunningItemType.Before
    var runningDate = RunningDate()
    var runningTime = DefaultRunningTime
    var locate = Me.locate.value

    fun writeRunningItem() = intent {
        val jwt = checkNotNull(Me.token.jwt) {
            "User must be registered."
        }
        val userId = checkNotNull(Me.token.userId) {
            "User must be registered."
        }
        writeRunningItemUseCase(
            jwt = jwt,
            userId = userId,
            item = RunningItemApiBody(
                title = title,
                itemType = itemType,
                meetingDate = runningDate.toDate(),
                runningTime = runningTime.toString(withWhitespace = false),
                locate = locate,
                ageFilter = ageFilter,
                maxPeopleCount = maxPeopleCount,
                gender = gender,
                message = message
            )
        ).onSuccess {
            reduce {
                RunningItemWriteState.Done
            }
        }.onFailure { exception ->
            emitException(exception)
        }
    }
}
