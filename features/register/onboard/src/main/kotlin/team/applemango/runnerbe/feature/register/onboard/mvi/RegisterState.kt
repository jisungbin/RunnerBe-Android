/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterState.kt] created by Ji Sungbin on 22. 2. 12. 오후 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.mvi

/**
 * UserRegisterUseCase state enum class
 * Failure 의 경우 emitException 으로 따로 통보 해주고 있으므로
 * 중복 state 를 막기 위해 failure state 생략
 */
enum class RegisterState {
    None, Request, Success
}
