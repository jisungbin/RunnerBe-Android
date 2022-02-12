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
 *
 * @property None 아무것도 하지 않은 초기화 단계
 * @property Request 요청 보낸 단계 (= loading)
 * @property Success 회원가입 성공
 * @property NullInformation 회원가입에 꼭 필요한 정보가 null 임 (프론트 에러)
 */
enum class RegisterState {
    None, Request, Success, NullInformation
}
