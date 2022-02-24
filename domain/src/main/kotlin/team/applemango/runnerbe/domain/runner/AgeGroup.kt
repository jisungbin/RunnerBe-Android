/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AgeGroup.kt] created by Ji Sungbin on 22. 2. 25. 오전 4:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runner

/**
 * 연령대 타입
 *
 * @property Start 초반
 * @property Middle 중반
 * @property End 후반
 */
enum class AgeGroupType {
    Start, Middle, End
}

/**
 * 연령대 종합
 *
 * @property age 연령대 (30대)
 * @property type 연령대 타입 (초반/중반/후반)
 */
data class AgeGroup(val age: Int, val type: AgeGroupType)
