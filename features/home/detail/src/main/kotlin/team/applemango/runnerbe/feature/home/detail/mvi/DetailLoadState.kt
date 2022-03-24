/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DetailState.kt] created by Ji Sungbin on 22. 3. 22. 오후 11:08
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail.mvi

/**
 * 러닝 아이템 상세 조회 상태
 *
 * @property Load 로딩 중
 * @property Done 로드 완료
 * @property NotVerified 아직 인증되지 않은 계정
 */
internal enum class DetailLoadState {
    Load,
    Done,
    NotVerified
}
