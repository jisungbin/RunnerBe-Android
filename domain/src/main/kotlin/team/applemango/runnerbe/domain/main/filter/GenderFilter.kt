/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GenderFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.filter

import team.applemango.runnerbe.domain.main.filter.GenderFilter.All
import team.applemango.runnerbe.domain.main.filter.GenderFilter.Create
import team.applemango.runnerbe.domain.runner.Gender

/**
 * 성별 필터
 *
 * @property All 전체
 * @property Create [Create.gender] 의 성별을 모집하는 아이템만 조회
 */
sealed class GenderFilter(val code: String) {
    object All : GenderFilter("A")
    data class Create(val gender: Gender) : GenderFilter(gender.code)
}
