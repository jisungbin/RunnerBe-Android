/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemSort.kt] created by Ji Sungbin on 22. 3. 29. 오전 11:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.common

/**
 * 러닝 아이템 정렬 타입 객체
 *
 * @property Nearby 거리순
 * @property Newest 최신순
 */
enum class RunningItemSort(val string: String) {
    Nearby("거리순"),
    Newest("최신순")
}
