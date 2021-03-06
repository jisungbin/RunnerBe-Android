/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemApiBodyMapper.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.mapper

import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBody
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBodyData
import java.text.SimpleDateFormat
import java.util.Locale

internal fun RunningItemApiBody.toData(): RunningItemApiBodyData {
    val meetingTime =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(meetingDate)
    val (address, latitude, longitude) = locate
    val (minAge, maxAge) = ageFilter
    return RunningItemApiBodyData(
        title = title,
        runnerGender = gender.code,
        runningTag = itemType.code,
        runningTime = runningTime,
        gatheringTime = meetingTime,
        locationInfo = address,
        gatherLatitude = latitude.toString(),
        gatherLongitude = longitude.toString(),
        ageMin = minAge ?: 20,
        ageMax = maxAge ?: 65,
        peopleNum = maxPeopleCount,
        contents = message
    )
}
