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
import team.applemango.runnerbe.domain.runningitem.usecase.WriteRunningItemUseCase
import team.applemango.runnerbe.shared.base.BaseViewModel

@HiltViewModel
class RunningItemWriteViewModel @Inject constructor(
    private val writeRunningItemUseCase: WriteRunningItemUseCase,
) : BaseViewModel()
