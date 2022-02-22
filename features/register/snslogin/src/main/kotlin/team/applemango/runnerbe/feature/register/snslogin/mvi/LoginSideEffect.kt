/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginSideEffect.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.mvi

internal sealed class LoginSideEffect {
    data class SaveUuid(val uuid: String) : LoginSideEffect()
    data class SaveJwt(val jwt: String) : LoginSideEffect()
}
