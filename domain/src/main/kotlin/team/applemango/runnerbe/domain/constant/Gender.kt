/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Gender.kt] created by Ji Sungbin on 22. 2. 10. 오전 5:15
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.constant

// TODO: 퀴어분들 대응
enum class Gender(val string: String, val code: String) {
    All("전체", "A"),
    Male("남성", "M"),
    Female("여성", "F")
}
