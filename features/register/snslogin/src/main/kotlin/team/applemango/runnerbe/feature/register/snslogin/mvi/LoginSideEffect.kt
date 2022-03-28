/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginSideEffect.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.mvi

import team.applemango.runnerbe.feature.register.snslogin.SnsLoginViewModel

/**
 * [SnsLoginViewModel] 에서 사용될 SideEffect 모델
 * 각 필드들의 NonNull 을 위해 따로따로 data class 를 만들어 줌
 */
internal sealed class LoginSideEffect {
    data class SaveUuid(val uuid: String) : LoginSideEffect()
    data class SaveUserToken(
        val userId: Int,
        val jwt: String,
    ) : LoginSideEffect()
}
