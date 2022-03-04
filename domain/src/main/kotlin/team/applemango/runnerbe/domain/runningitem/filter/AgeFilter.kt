/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AgeFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:42
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.filter

/**
 * 나이 필터
 *
 * 필터를 적용하지 않은 상태도 있어야 하기 때문에
 * 모든 필드는 Nullable 함 -> AgeRange 가 아닌 AgeFilter 인 이유
 *
 * @property min 최소 나이
 * @property max 최대 나이
 */
data class AgeFilter(val min: Int?, val max: Int?) {
    companion object {
        val None = AgeFilter(
            min = null,
            max = null
        )
    }

    override fun toString() = "$min-$max"
    fun getCode(ageBuilder: AgeFilter.() -> Int?) = ageBuilder(this)?.toString() ?: "N"
}
