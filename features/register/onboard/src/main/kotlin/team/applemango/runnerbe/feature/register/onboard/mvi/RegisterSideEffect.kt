/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterSideEffect.kt] created by Ji Sungbin on 22. 2. 12. 오후 2:13
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.mvi

import team.applemango.runnerbe.feature.register.onboard.constant.Step

internal sealed class RegisterSideEffect {
    data class NavigateToNextStep(val nextStep: Step) : RegisterSideEffect()
    object ResetStep : RegisterSideEffect()
}
