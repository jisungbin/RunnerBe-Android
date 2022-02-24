/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AgeFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:42
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.constant.load

/**
 * 나이 필터
 *
 * 필터를 적용하지 않은 상태도 있어야 하기 때문에
 * 모든 필드는 Nullable 함
 *
 * @property min 최소 연령대
 * @property max 최대 연령대
 */
data class AgeRange(val min: Int?, val max: Int?) {
    override fun toString() = "$min-$max"
    fun getCode(ageBuilder: AgeRange.() -> Int?) = ageBuilder(this)?.toString() ?: "N"
}
