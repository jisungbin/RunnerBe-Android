/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardViewModel.kt] created by Ji Sungbin on 22. 2. 11. 오전 4:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard

import team.applemango.runnerbe.domain.login.usecase.CheckEmailDuplicateUseCase
import team.applemango.runnerbe.shared.base.BaseViewModel

class OnboardViewModel(
    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
) : BaseViewModel() {
    suspend fun checkEmailDuplicate(email: String) =
        checkEmailDuplicateUseCase(email).getOrElse { exception ->
            emitException(exception)
            false
        }
}
