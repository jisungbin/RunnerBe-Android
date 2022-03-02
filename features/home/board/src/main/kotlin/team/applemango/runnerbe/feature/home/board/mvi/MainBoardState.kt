/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardState.kt] created by Ji Sungbin on 22. 3. 2. 오후 8:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.mvi

/**
 * @property None 성공했거나 초기 상태는 취할 액션이 없기 때문에 None 하나로 Success 까지 포함함
 * @property Failure 요청 실패
 */
enum class MainBoardState {
    None,
    Failure
}
