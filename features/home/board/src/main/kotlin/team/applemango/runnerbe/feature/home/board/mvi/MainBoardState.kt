/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardState.kt] created by Ji Sungbin on 22. 3. 2. 오후 8:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.mvi

import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.feature.home.board.MainBoardViewModel
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.BookmarkToggleRequestFail
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.NonRegisterUser
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.RunningItemLoadFail
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.RunningItemLoaded
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState.RunningItemLoading

/**
 * MainBoard 전체 상태를 담고 있으며,
 * RunningItem 관련 상태들은 [RunningItemsState] 로
 * 변환됨
 *
 * @property NonRegisterUser 비회원 유저로써 아직 특정 기능 이용이 불기능함
 * [MainBoardViewModel] 에서 UseCase 의 result 가 `아직 인증되지 않은 계정` 이라면
 * [NonRegisterUser] state 를 쏴서 UI 처리를 해주기 위함
 * @property RunningItemLoading 러닝 아이템 목록 조회중
 * @property RunningItemLoadFail 러닝 아이템 목록 조회 실패
 * @property RunningItemLoaded 러닝 아이템 목록 조회 성공
 * @property BookmarkToggleRequestFail 북마크 갱신 request fail 일 경우에,
 * [BookmarkToggleRequestFail] state 를 쏴서 북마크 아이콘 composable toggle 을
 * rollback 해주기 위함
 */
internal sealed class MainBoardState {
    object NonRegisterUser : MainBoardState()
    object RunningItemLoading : MainBoardState()
    object RunningItemLoadFail : MainBoardState()
    data class RunningItemLoaded(val items: List<RunningItem>) : MainBoardState()
    object BookmarkToggleRequestFail : MainBoardState()
}
