/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [WriteRunningItemUseCase.kt] created by Ji Sungbin on 22. 2. 24. 오후 9:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.usecase

import java.text.SimpleDateFormat
import java.util.Locale
import team.applemango.runnerbe.domain.main.model.write.RunningItemBody
import team.applemango.runnerbe.domain.main.model.write.RunningItemBodyData
import team.applemango.runnerbe.domain.main.repository.MainRepository

class WriteRunningItemUseCase(private val repo: MainRepository) {
    suspend operator fun invoke(
        jwt: String,
        userId: Int,
        item: RunningItemBody,
    ) = runCatching {
        val meetingTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(item.meetingDate)
        val (address, latitude, longitude) = item.locate
        val (minAge, maxAge) = item.ageRange

        repo.writeRunningItem(
            jwt = jwt,
            userId = userId,
            item = RunningItemBodyData(
                title = item.title,
                runnerGender = item.gender.code,
                runningTag = item.itemType.code,
                runningTime = item.runningTime,
                gatheringTime = meetingTime,
                locationInfo = address,
                gatherLatitude = latitude.toString(),
                gatherLongitude = longitude.toString(),
                ageMin = minAge ?: 20,
                ageMax = maxAge ?: 65,
                peopleNum = item.maxPeopleCount,
                contents = item.message
            )
        )
    }
}
