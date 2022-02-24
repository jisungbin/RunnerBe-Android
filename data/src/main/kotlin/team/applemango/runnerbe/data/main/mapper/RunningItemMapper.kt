/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemMapper.kt] created by Ji Sungbin on 22. 2. 24. 오후 3:03
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.mapper

import team.applemango.runnerbe.data.main.mapper.MappingType.InformationApiFields
import team.applemango.runnerbe.data.main.mapper.MappingType.MainPageApiFields
import team.applemango.runnerbe.data.main.model.runningitem.RunningItemsResponse
import team.applemango.runnerbe.domain.main.common.RunningItemType
import team.applemango.runnerbe.domain.main.filter.AgeFilter
import team.applemango.runnerbe.domain.main.model.common.Locate
import team.applemango.runnerbe.domain.main.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runner.Gender
import team.applemango.runnerbe.domain.runner.Job
import team.applemango.runnerbe.shared.domain.extension.convertNullableString
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage
import team.applemango.runnerbe.shared.domain.requireValueExceptionMessage

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

internal fun RunningItemsResponse.toDomain(type: MappingType): List<RunningItem> {
    if (result.isNullOrEmpty()) return emptyList()
    return result.map { data ->
        checkNotNull(data) { requireValueExceptionMessage("RunningItemResponse.result item") }
        RunningItem(
            itemId = requireNotNull(data.postId) { requireFieldExceptionMessage("postId") },
            ownerId = requireNotNull(data.postUserId) { requireFieldExceptionMessage("postUserId") },
            ownerNickName = when (type) {
                MainPageApiFields -> requireNotNull(data.nickName) {
                    requireFieldExceptionMessage("nickName")
                }
                InformationApiFields -> ""
            },
            ownerProfileImageUrl = when (type) {
                MainPageApiFields -> requireNotNull(data.profileImageUrl) {
                    requireFieldExceptionMessage("profileImageUrl")
                }.convertNullableString() ?: DefaultProfileImageUrl
                InformationApiFields -> DefaultProfileImageUrl
            },
            createdAt = requireNotNull(data.postingTime) { requireFieldExceptionMessage("postingTime") },
            bookmarkCount = when (type) {
                MainPageApiFields -> requireNotNull(data.bookMarkNumber) {
                    requireFieldExceptionMessage("bookMarkNumber")
                }
                InformationApiFields -> DefaultIntValue
            },
            runningType = RunningItemType.values().first {
                val runningTypeCode = requireNotNull(data.runningTag) {
                    requireFieldExceptionMessage("runningTag")
                }
                it.code == runningTypeCode
            },
            finish = when (type) {
                MainPageApiFields -> requireNotNull(data.whetherEnd) {
                    requireFieldExceptionMessage("whetherEnd")
                }.toBoolean()
                InformationApiFields -> false
            },
            maxRunnerCount = when (type) {
                MainPageApiFields -> DefaultIntValue
                InformationApiFields -> requireNotNull(data.peopleNum) {
                    requireFieldExceptionMessage("peopleNum") // ex_최대 4명
                }.split(" ")[1].split("명")[0].toInt()
            },
            title = requireNotNull(data.title) { requireFieldExceptionMessage("title") },
            gender = Gender.values().first {
                val genderCode = requireNotNull(data.gender) {
                    requireFieldExceptionMessage("gender")
                }
                it.code == genderCode
            },
            jobs = when (type) {
                MainPageApiFields -> requireNotNull(data.job) { requireFieldExceptionMessage("job") }
                    .split(",")
                    .map { jobCode ->
                        Job.values().first { it.string == jobCode }
                    }
                InformationApiFields -> emptyList()
            },
            ageFilter = run {
                val age = requireNotNull(data.age) { requireFieldExceptionMessage("age") }
                val (minAge, maxAge) = age.split("-").map(String::toInt)
                AgeFilter(min = minAge, max = maxAge)
            },
            runningTime = requireNotNull(data.runningTime) { requireFieldExceptionMessage("runningTime") },
            locate = run {
                val address = requireNotNull(data.locationInfo) {
                    requireFieldExceptionMessage("locationInfo")
                }
                val latitudeString = requireNotNull(data.gatherLatitude) {
                    requireFieldExceptionMessage("gatherLatitude")
                }
                val longitudeString = requireNotNull(data.gatherLongitude) {
                    requireFieldExceptionMessage("gatherLongitude")
                }
                Locate(
                    address = address,
                    latitude = latitudeString.toDouble(),
                    longitude = longitudeString.toDouble(),
                )
            },
            distance = when (type) {
                MainPageApiFields -> requireNotNull(data.distance) {
                    requireFieldExceptionMessage("distance")
                }.toFloat()
                InformationApiFields -> DefaultIntValue.toFloat()
            },
            meetingDate = requireNotNull(data.gatheringTime) { requireFieldExceptionMessage("gatheringTime") },
            message = when (type) {
                MainPageApiFields -> ""
                InformationApiFields -> requireNotNull(data.contents) {
                    requireFieldExceptionMessage("contents")
                }
            }
        )
    }
}