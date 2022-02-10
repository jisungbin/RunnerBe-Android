/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [EmailVerifyState.kt] created by Ji Sungbin on 22. 2. 10. 오후 5:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.constant

internal sealed class EmailVerifyState {
    object None : EmailVerifyState()
    object Sent : EmailVerifyState()
    object Duplicate : EmailVerifyState()
    object ErrorUuid : EmailVerifyState()
    data class Exception(val throwable: Throwable) : EmailVerifyState()
}
