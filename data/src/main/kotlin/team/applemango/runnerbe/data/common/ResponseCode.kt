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

internal const val FailJwtProcessing = 2000
internal const val FailUuidProcessing = 2001
internal const val FailBirthdayProcessing = 2002
internal const val FailGenderProcessing = 2003
internal const val FailJobProcessing = 2004

internal const val FailJwtVerification = 3000
