/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AgeFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:42
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.constant

/**
 * 나이 필터
 *
 * @property None 모든 나이
 * @property Age [Age.age] 이상 나이만 모집하는 아이템만 조회
 */
sealed class AgeFilter(val code: String) {
    object None : AgeFilter("A")
    data class Age(val age: Int) : AgeFilter(age.toString())
}
