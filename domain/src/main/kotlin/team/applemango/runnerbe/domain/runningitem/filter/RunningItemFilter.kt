/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:30
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.filter

/**
 * 모집 타입
 *
 * @property Distance 거리 순
 * @property New 최신 순
 * @property Hot 찜 많은 순
 */
enum class RunningItemFilter(val code: String) {
    Distance("D"),
    New("R"),
    Hot("B")
}
