/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardState.kt] created by Ji Sungbin on 22. 3. 2. 오후 8:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.mvi

import team.applemango.runnerbe.feature.home.board.MainBoardViewModel
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.NonRegisterUser
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.None

/**
 * [MainBoardViewModel] 에서 UseCase 의 result 가 `아직 인증되지 않은 계정` 이라면
 * [NonRegisterUser] state 를 쏴서 UI 처리를 해주기 위함
 *
 * @property None 성공, 초기, 에러 상태는 UI 에서 따로 취할 액션이
 * 없기 때문에 None 하나로 모두 포함함
 * @property NonRegisterUser 비회원 유저로써 아직 특정 기능 이용이 불기능함
 */
internal enum class MainBoardState {
    None,
    NonRegisterUser
}
