/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginState.kt] created by Ji Sungbin on 22. 2. 23. 오전 6:58
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.constant

/**
 * @property None 아무런 상태도 아닌 초기값
 * @property Done 로그인 성공
 * @property Registered 이미 가입된 유저 (회원가입 모두 완료)
 */
internal enum class LoginState {
    None,
    Done,
    Registered
}
