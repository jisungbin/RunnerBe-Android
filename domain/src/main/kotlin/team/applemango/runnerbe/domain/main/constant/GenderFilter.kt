/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GenderFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.constant

/**
 * 성별 필터
 *
 * @property All 전체
 * @property Female 여성만
 * @property Male 남성만
 */
enum class GenderFilter(val code: String) {
    All("A"),
    Female("F"),
    Male("M")
}
