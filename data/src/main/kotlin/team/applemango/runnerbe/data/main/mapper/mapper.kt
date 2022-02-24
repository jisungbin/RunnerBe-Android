/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [mapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 3:03
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.mapper

import team.applemango.runnerbe.data.main.model.RunningItemResponse
import team.applemango.runnerbe.data.util.extension.convertNullableString
import team.applemango.runnerbe.data.util.requireFieldExceptionMessage
import team.applemango.runnerbe.data.util.requireValueExceptionMessage
import team.applemango.runnerbe.domain.main.constant.AgeRange
import team.applemango.runnerbe.domain.main.constant.GenderFilter
import team.applemango.runnerbe.domain.main.constant.RunningItemType
import team.applemango.runnerbe.domain.main.model.Locate
import team.applemango.runnerbe.domain.main.model.RunningItem
import team.applemango.runnerbe.domain.register.login.constant.Job

private const val DefaultProfileImageUrl =
    "https://github.com/applemango-runnerbe/applemango-runnerbe.github.io/blob/main/Profile_28.png?raw=true"

internal fun RunningItemResponse.toDomain(): List<RunningItem> {
    if (result.isNullOrEmpty()) return emptyList()
    return result.map { data ->
        checkNotNull(data) { requireValueExceptionMessage("RunningItemResponse.result item") }
        RunningItem(
            itemId = requireNotNull(data.postId) { requireFieldExceptionMessage("postId") },
            ownerId = requireNotNull(data.postUserId) { requireFieldExceptionMessage("postUserId") },
            ownerNickName = requireNotNull(data.nickName) { requireFieldExceptionMessage("nickName") },
            ownerProfileImageUrl = requireNotNull(data.profileImageUrl) {
                requireFieldExceptionMessage("profileImageUrl")
            }.convertNullableString() ?: DefaultProfileImageUrl,
            createdAt = requireNotNull(data.postingTime) { requireFieldExceptionMessage("postingTime") },
            bookmarkCount = requireNotNull(data.bookMarkNumber) { requireFieldExceptionMessage("bookMarkNumber") },
            runningType = RunningItemType.values().first {
                val dataCode =
                    requireNotNull(data.runningTag) { requireFieldExceptionMessage("runningTag") }
                it.code == dataCode
            },
            finish = requireNotNull(data.whetherEnd) { requireFieldExceptionMessage("whetherEnd") }.toBoolean(),
            title = requireNotNull(data.title) { requireFieldExceptionMessage("title") },
            gender = GenderFilter.values().first {
                val dataGender =
                    requireNotNull(data.gender) { requireFieldExceptionMessage("gender") }
                it.code == dataGender
            },
            jobs = requireNotNull(data.job) { requireFieldExceptionMessage("job") }
                .split(",")
                .map { jobCode ->
                    Job.values().first { it.code == jobCode }
                },
            ageRange = run {
                val age = requireNotNull(data.age) { requireFieldExceptionMessage("age") }
                val (minAge, maxAge) = age.split("-").map(String::toInt)
                AgeRange(min = minAge, max = maxAge)
            },
            runningTime = requireNotNull(data.runningTime) { requireFieldExceptionMessage("runningTime") },
            locate = run {
                val address =
                    requireNotNull(data.locationInfo) { requireFieldExceptionMessage("locationInfo") }
                val latitudeString =
                    requireNotNull(data.gatherLatitude) { requireFieldExceptionMessage("gatherLatitude") }
                val longitudeString =
                    requireNotNull(data.gatherLongitude) { requireFieldExceptionMessage("gatherLongitude") }
                Locate(
                    address = address,
                    latitude = latitudeString.toDouble(),
                    longitude = longitudeString.toDouble(),
                )
            },
            distance = requireNotNull(data.distance) { requireFieldExceptionMessage("distance") }.toFloat(),
            meetingDate = requireNotNull(data.gatheringTime) { requireFieldExceptionMessage("gatheringTime") }
        )
    }
}
