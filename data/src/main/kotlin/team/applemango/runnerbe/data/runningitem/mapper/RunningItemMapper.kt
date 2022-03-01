/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemMapper.kt] created by Ji Sungbin on 22. 2. 25. 오전 5:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.mapper

import java.text.SimpleDateFormat
import java.util.Locale
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.InformationApiFields
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.MainPageApiFields
import team.applemango.runnerbe.data.runningitem.model.runningitem.RunningItemData
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.model.common.Time
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.shared.domain.extension.convertNullableString
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

/**
 * API Call 마다 불러오는 [RunningItem] 필드들이 다름
 *
 * @property MainPageApiFields 메인 페이지 API Call result
 * - peopleNum, contents 필드 없음
 * @property InformationApiFields  게시글 상세 페이지 API Call result
 * - nickName, profileImageUrl, bookMarkNumber, whetherEnd, job, distance 필드 없음
 */
internal enum class MappingType {
    MainPageApiFields,
    InformationApiFields,
}

private const val DefaultIntValue = -1
private const val DefaultProfileImageUrl =
    "https://github.com/applemango-runnerbe/applemango-runnerbe.github.io/blob/main/Profile_28.png?raw=true"

internal fun RunningItemData.toDomain(type: MappingType) = RunningItem(
    itemId = requireNotNull(postId) { requireFieldExceptionMessage("postId") },
    ownerId = requireNotNull(postUserId) { requireFieldExceptionMessage("postUserId") },
    ownerNickName = when (type) {
        MainPageApiFields -> requireNotNull(nickName) {
            requireFieldExceptionMessage("nickName")
        }
        InformationApiFields -> ""
    },
    ownerProfileImageUrl = when (type) {
        MainPageApiFields -> requireNotNull(profileImageUrl) {
            requireFieldExceptionMessage("profileImageUrl")
        }.convertNullableString() ?: DefaultProfileImageUrl
        InformationApiFields -> DefaultProfileImageUrl
    },
    createdAt = requireNotNull(postingTime) { requireFieldExceptionMessage("postingTime") },
    bookmarkCount = when (type) {
        MainPageApiFields -> requireNotNull(bookMarkNumber) {
            requireFieldExceptionMessage("bookMarkNumber")
        }
        InformationApiFields -> DefaultIntValue
    },
    runningType = RunningItemType.values().first {
        val runningTypeCode = requireNotNull(runningTag) {
            requireFieldExceptionMessage("runningTag")
        }
        it.code == runningTypeCode
    },
    finish = when (type) {
        MainPageApiFields -> requireNotNull(whetherEnd) {
            requireFieldExceptionMessage("whetherEnd")
        }.toBoolean()
        InformationApiFields -> false
    },
    maxRunnerCount = when (type) {
        MainPageApiFields -> DefaultIntValue
        InformationApiFields -> requireNotNull(peopleNum) {
            requireFieldExceptionMessage("peopleNum") // ex_최대 4명
        }.split(" ")[1].split("명")[0].toInt()
    },
    title = requireNotNull(title) { requireFieldExceptionMessage("title") },
    gender = Gender.values().first {
        val genderCode = requireNotNull(gender) {
            requireFieldExceptionMessage("gender")
        }
        it.code == genderCode
    },
    jobs = when (type) {
        MainPageApiFields -> requireNotNull(job) { requireFieldExceptionMessage("job") }
            .split(",")
            .map { jobCode ->
                Job.values().first { it.string == jobCode }
            }
        InformationApiFields -> emptyList()
    },
    ageFilter = run {
        val age = requireNotNull(age) { requireFieldExceptionMessage("age") }
        val (minAge, maxAge) = age.split("-").map(String::toInt)
        AgeFilter(min = minAge, max = maxAge)
    },
    runningTime = requireNotNull(runningTime) {
        requireFieldExceptionMessage("runningTime")
    }.let { timeString ->
        val (hour, minute, second) = timeString.split(":").map(String::toInt)
        Time(hour = hour, minute = minute, second = second)
    },
    locate = run {
        val address = requireNotNull(locationInfo) {
            requireFieldExceptionMessage("locationInfo")
        }
        val latitudeString = requireNotNull(gatherLatitude) {
            requireFieldExceptionMessage("gatherLatitude")
        }
        val longitudeString = requireNotNull(gatherLongitude) {
            requireFieldExceptionMessage("gatherLongitude")
        }
        Locate(
            address = address,
            latitude = latitudeString.toDouble(),
            longitude = longitudeString.toDouble(),
        )
    },
    distance = when (type) {
        MainPageApiFields -> requireNotNull(distance) {
            requireFieldExceptionMessage("distance")
        }.toFloat()
        InformationApiFields -> DefaultIntValue.toFloat()
    },
    meetingDate = requireNotNull(gatheringTime) {
        requireFieldExceptionMessage("gatheringTime")
    }.let { dateString ->
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.parse(dateString)
            ?: throw Exception("Server response time has not allowed pattern: $dateString")
    },
    message = when (type) {
        MainPageApiFields -> ""
        InformationApiFields -> requireNotNull(contents) {
            requireFieldExceptionMessage("contents")
        }
    }
)
