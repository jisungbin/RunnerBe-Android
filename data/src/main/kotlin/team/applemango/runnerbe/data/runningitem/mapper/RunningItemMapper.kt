/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemMapper.kt] created by Ji Sungbin on 22. 2. 25. 오전 5:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.mapper

import team.applemango.runnerbe.data.runningitem.model.runningitem.RunningItemData
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.model.common.Time
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.shared.domain.extension.convertNullableString
import team.applemango.runnerbe.shared.domain.extension.toBoolean
import team.applemango.runnerbe.shared.domain.util.requireFieldNullMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DefaultIntValue = 0
private const val DefaultProfileImageUrl = "https://runnerbe.xyz/assets/image/default_profile.png"

private val serverDateFormat by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
}

internal fun RunningItemData.toDomain() = RunningItem(
    itemId = requireNotNull(postId) {
        requireFieldNullMessage("postId")
    },
    ownerId = requireNotNull(postUserId) {
        requireFieldNullMessage("postUserId")
    },
    ownerNickName = nickName ?: EmptyString,
    ownerProfileImageUrl = profileImageUrl?.convertNullableString() ?: DefaultProfileImageUrl,
    createdAt = run {
        postingTime?.let { time ->
            serverDateFormat.parse(time)
                ?: throw Exception("Server response time has not allowed pattern: $postingTime")
        } ?: Date()
    },
    bookmarkCount = bookMarkNumber ?: DefaultIntValue,
    runningType = RunningItemType.values().first {
        requireNotNull(runningTag) {
            requireFieldNullMessage("runningTag")
        }
        it.code == runningTag
    },
    finish = requireNotNull(whetherEnd) {
        requireFieldNullMessage("whetherEnd")
    }.toBoolean(),
    maxRunnerCount = peopleNum?.split(" ")?.get(1)?.split("명")?.get(0)?.toInt() ?: DefaultIntValue,
    title = requireNotNull(title) {
        requireFieldNullMessage("title")
    },
    gender = Gender.values().first {
        val genderCode = requireNotNull(gender) {
            requireFieldNullMessage("gender")
        }
        it.string == genderCode
    },
    jobs = job?.split(",")?.map { jobCode ->
        Job.values().first { it.name == jobCode }
    } ?: emptyList(),
    ageFilter = run {
        requireNotNull(age) {
            requireFieldNullMessage("age")
        }
        val (minAge, maxAge) = age.split("-").map(String::toInt)
        AgeFilter(min = minAge, max = maxAge)
    },
    runningTime = run {
        requireNotNull(runningTime) {
            requireFieldNullMessage("runningTime")
        }
        val (hour, minute, second) = runningTime.split(":").map(String::toInt)
        Time(hour = hour, minute = minute, second = second)
    },
    locate = run {
        requireNotNull(locationInfo) {
            requireFieldNullMessage("locationInfo")
        }
        val latitudeString = gatherLatitude ?: DefaultIntValue.toString()
        val longitudeString = gatherLongitude ?: DefaultIntValue.toString()
        Locate(
            address = locationInfo,
            latitude = latitudeString.toDouble(),
            longitude = longitudeString.toDouble(),
        )
    },
    distance = distance?.toFloat() ?: DefaultIntValue.toFloat(),
    meetingDate = run {
        requireNotNull(gatheringTime) {
            requireFieldNullMessage("gatheringTime")
        }
        serverDateFormat.parse(gatheringTime)
            ?: throw Exception("Server response time has not allowed pattern: $gatheringTime")
    },
    message = contents ?: EmptyString,
    attendance = (attendance ?: DefaultIntValue).toBoolean(),
    bookmarked = (bookMark ?: DefaultIntValue).toBoolean(),
)
