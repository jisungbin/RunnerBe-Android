/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemType.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:34
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.common

/**
 * @property Before 출근 전
 * @property After 퇴근 후
 * @property Off 휴일
 */
enum class RunningItemType(val code: String) {
    Before("B"),
    After("A"),
    Off("H");

    /**
     * 출근 전 - 06:00 AM, 퇴근 후 06:00 PM, 휴일 - 12:00 PM
     */
    fun toDefaultDateTime() = when (this) {
        Before -> "06:00 AM"
        After -> "06:00 PM"
        Off -> "12:00 PM"
    }
}
