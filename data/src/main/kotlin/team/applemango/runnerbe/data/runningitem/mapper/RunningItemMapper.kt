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
import java.util.Date
import java.util.Locale
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.BookmarkApiFields
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.InformationApiFields
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.MainPageApiFields
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.MyPageJoinRunningItemFields
import team.applemango.runnerbe.data.runningitem.mapper.MappingType.MyPageOwnRunningItemFields
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
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage

/**
 * API Call 마다 불러오는 [RunningItem] 필드들이 다름
 *
 * @property MainPageApiFields 메인 페이지 API Call result
 * - peopleNum, contents, attendance, bookmarked 필드 없음
 * @property InformationApiFields  게시글 상세 페이지 API Call result
 * - nickName, profileImageUrl, bookMarkNumber, whetherEnd, job, distance,
 * attendance, bookmarked 필드 없음
 * @property BookmarkApiFields 찜 목록 조회 API Call result
 * - bookMarkNumber, peopleNum, job, distance, contents, attendance, bookmarked 필드 없음
 * @property MyPageOwnRunningItemFields 마이 페이지 정보 조회 API - myPosting 부분
 * postingTime, bookMarkNumber, peopleNum, gatherLatitude, gatherLongitude,
 * distance, contents, attendance, bookmarked
 * @property MyPageJoinRunningItemFields 마이 페이지 정보 조회 API - myRunning 부분
 * postingTime, bookMarkNumber, peopleNum, gatherLatitude, gatherLongitude,
 * distance, contents
 */
internal enum class MappingType {
    MainPageApiFields,
    InformationApiFields,
    BookmarkApiFields,
    MyPageOwnRunningItemFields,
    MyPageJoinRunningItemFields
}

private const val DefaultIntValue = 0
private const val DefaultProfileImageUrl =
    "https://github.com/applemango-runnerbe/applemango-runnerbe.github.io/blob/main/Profile_28.png?raw=true"

private val serverDateFormat by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
}

internal fun RunningItemData.toDomain(type: MappingType) = RunningItem(
    itemId = requireNotNull(postId) {
        requireFieldNullMessage("postId")
    },
    ownerId = requireNotNull(postUserId) {
        requireFieldNullMessage("postUserId")
    },
    ownerNickName = when (type) {
        MainPageApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> requireNotNull(nickName) {
            requireFieldNullMessage("nickName")
        }
        InformationApiFields -> EmptyString
    },
    ownerProfileImageUrl = when (type) {
        MainPageApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> requireNotNull(profileImageUrl) {
            requireFieldNullMessage("profileImageUrl")
        }.convertNullableString() ?: DefaultProfileImageUrl
        InformationApiFields -> DefaultProfileImageUrl
    },
    createdAt = when (type) {
        MainPageApiFields, InformationApiFields,
        BookmarkApiFields,
        -> run {
            requireNotNull(postingTime) {
                requireFieldNullMessage("postingTime")
            }
            serverDateFormat.parse(postingTime)
                ?: throw Exception("Server response time has not allowed pattern: $postingTime")
        }
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields -> Date()
    },
    bookmarkCount = when (type) {
        MainPageApiFields -> requireNotNull(bookMarkNumber) {
            requireFieldNullMessage("bookMarkNumber")
        }
        InformationApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> DefaultIntValue
    },
    runningType = RunningItemType.values().first {
        requireNotNull(runningTag) {
            requireFieldNullMessage("runningTag")
        }
        it.code == runningTag
    },
    finish = requireNotNull(whetherEnd) {
        requireFieldNullMessage("whetherEnd")
    }.toBoolean(),
    maxRunnerCount = when (type) {
        MainPageApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> DefaultIntValue
        InformationApiFields -> requireNotNull(peopleNum) {
            requireFieldNullMessage("peopleNum") // ex_최대 4명
        }.split(" ")[1].split("명")[0].toInt()
    },
    title = requireNotNull(title) {
        requireFieldNullMessage("title")
    },
    gender = Gender.values().first {
        val genderCode = requireNotNull(gender) {
            requireFieldNullMessage("gender")
        }
        it.code == genderCode
    },
    jobs = when (type) {
        MainPageApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> run {
            requireNotNull(job) {
                requireFieldNullMessage("job")
            }
            job.split(",").map { jobCode ->
                Job.values().first { it.string == jobCode }
            }
        }
        InformationApiFields, BookmarkApiFields -> emptyList()
    },
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
        val latitudeString = when (type) {
            MainPageApiFields,
            InformationApiFields, BookmarkApiFields,
            -> requireNotNull(gatherLatitude) {
                requireFieldNullMessage("gatherLatitude")
            }
            MyPageOwnRunningItemFields, MyPageJoinRunningItemFields -> DefaultIntValue.toString()
        }
        val longitudeString = when (type) {
            MainPageApiFields,
            InformationApiFields, BookmarkApiFields,
            -> requireNotNull(gatherLongitude) {
                requireFieldNullMessage("gatherLongitude")
            }
            MyPageOwnRunningItemFields, MyPageJoinRunningItemFields -> DefaultIntValue.toString()
        }
        Locate(
            address = locationInfo,
            latitude = latitudeString.toDouble(),
            longitude = longitudeString.toDouble(),
        )
    },
    distance = when (type) {
        MainPageApiFields -> requireNotNull(distance) {
            requireFieldNullMessage("distance")
        }.toFloat()
        InformationApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> DefaultIntValue.toFloat()
    },
    meetingDate = run {
        requireNotNull(gatheringTime) {
            requireFieldNullMessage("gatheringTime")
        }
        serverDateFormat.parse(gatheringTime)
            ?: throw Exception("Server response time has not allowed pattern: $gatheringTime")
    },
    message = when (type) {
        MainPageApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, MyPageJoinRunningItemFields,
        -> EmptyString
        InformationApiFields -> requireNotNull(contents) {
            requireFieldNullMessage("contents")
        }
    },
    attendance = when (type) {
        MainPageApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, InformationApiFields,
        -> DefaultIntValue
        MyPageJoinRunningItemFields -> requireNotNull(attendance) {
            requireFieldNullMessage("attendance")
        }
    }.toBoolean(),
    bookmarked = when (type) {
        MainPageApiFields, BookmarkApiFields,
        MyPageOwnRunningItemFields, InformationApiFields,
        -> DefaultIntValue
        MyPageJoinRunningItemFields -> requireNotNull(bookMark) {
            requireFieldNullMessage("bookMark")
        }
    }.toBoolean(),
)
