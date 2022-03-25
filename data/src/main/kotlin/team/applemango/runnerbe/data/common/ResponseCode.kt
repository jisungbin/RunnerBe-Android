/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ResponseCode.kt] created by Ji Sungbin on 22. 3. 25. 오후 9:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.common

internal const val Success = 1000
internal const val SuccessAuthMember = 1001
internal const val SuccessNonMember = 1002
internal const val SuccessWriter = 1003
internal const val SuccessNonWriter = 1004
internal const val SuccessEmailVerified = 1005
internal const val SuccessEmployeeIdPending = 1006
internal const val SuccessWaitingVerify = 1007
internal const val SuccessVerifiedMemberFirstEntry = 1008
internal const val SuccessOwnerBeforeJoin = 1009
internal const val SuccessPartyBeforeJoin = 1010
internal const val SuccessOwnerAfterJoinManageWait = 1011
internal const val SuccessOwnerAfterJoinManageDone = 1012
internal const val SuccessPartyAfterJoin = 1013
internal const val SuccessPartyAlreadyJoin = 1014
internal const val SuccessPartyAlreadyJoinAndBookmarked = 1015
internal const val SuccessPartyAlreadyJoinAndNotBookmark = 1016
internal const val SuccessPartyBeforeJoinAndBookmarked = 1017
internal const val SuccessPartyBeforeJoinAndNotBookmark = 1018
internal const val SuccessOwnerAndBookmarked = 1019
internal const val SuccessOwnerAndNotBookmark = 1020
internal const val SuccessJwtVerification = 1100

internal const val FailJwtRead = 2000
internal const val FailUuidRead = 2001
internal const val FailBirthdayRead = 2002
internal const val FailGenderRead = 2003
internal const val FailJobCodeRead = 2004
internal const val FailUnknownGenderType = 2005
internal const val FailUnknownJobCode = 2006
internal const val FailUnknownEmailType = 2007
internal const val FailNicknameReadWhenSignup = 2008
internal const val FailOverflowNicknameLength = 2009
internal const val FailMatchJwtUserId = 2010
internal const val FailUserIdRead = 2011
internal const val FailUnknownUserIdType = 2012
internal const val FailNicknameReadWhenChange = 2013
internal const val FailRunningItemTitleRead = 2014
internal const val FailRunningItemMeetingTimeRead = 2015
internal const val FailRunningItemRunningTimeRead = 2016
internal const val FailRunningItemMeetingLongitudeRead = 2017
internal const val FailRunningItemMeetingLatitudeRead = 2018
internal const val FailRunningItemMeetingAddressRead = 2019
internal const val FailRunningItemTypeRead = 2020
internal const val FailRunningItemMinAgeRead = 2021
internal const val FailRunningItemMaxAgeRead = 2022
internal const val FailRunningItemMaxPeopleCountRead = 2023
internal const val FailRunningItemAllowGenderRead = 2024
internal const val FailOverflowRunningItemTitle = 2025
internal const val FailOverflowRunningItemContent = 2026
internal const val FailUnknownRunningItemMinAgeType = 2027
internal const val FailUnknownRunningItemMaxAgeType = 2028
internal const val FailUnknownRunningItemMaxPeopleCount = 2029
internal const val FailUnknownRunningItemType = 2030
internal const val FailUnknownRunningItemAllowGenderType = 2031
internal const val FailEmployeeIdUrlRead = 2032

internal const val FailJwtVerification = 3000

// TODO: ResponseCode 다 추가
// 현재는 너무 똑같은 역할을 하는 코드들이 많아서
// 이걸 다 추가하는건 엄청난 비효율 같음
// 백엔드 개발자의 경력이 중요해...
