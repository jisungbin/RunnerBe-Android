/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemBody.kt] created by Ji Sungbin on 22. 2. 24. 오후 7:13
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model.write

/**
 * API Call 전용 Body class
 */
data class RunningItemBodyData(
    val title: String,
    val runnerGender: String,
    val runningTag: String,
    val runningTime: String,
    val gatheringTime: String,
    val locationInfo: String,
    val gatherLatitude: String,
    val gatherLongitude: String,
    val ageMin: Int,
    val ageMax: Int,
    val peopleNum: Int,
    val contents: String,
)
