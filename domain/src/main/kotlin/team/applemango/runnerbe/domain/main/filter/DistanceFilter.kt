/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DistanceFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:33
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.filter

/**
 * 거리 필터
 *
 * @property None 전체
 * @property Create [Create.km] 킬로미터 안에 있는 아이템만 조회
 */
sealed class DistanceFilter(val code: String) {
    object None : DistanceFilter("N")
    data class Create(val km: Int) : DistanceFilter(km.toString())
}
