/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ResultItem.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model

import team.applemango.runnerbe.domain.main.constant.AgeRange
import team.applemango.runnerbe.domain.main.constant.GenderFilter
import team.applemango.runnerbe.domain.main.constant.RunningItemType
import team.applemango.runnerbe.domain.register.login.constant.Job

/**
 * @property itemId 게시글 아이템 아이디
 */
data class RunningItem(
    val itemId: Int,
    val ownerId: Int,
    val ownerNickName: String? = null,
    val ownerProfileImageUrl: String,
    val createdAt: String,
    val title: String,
    val gender: GenderFilter,
    val jobs: List<Job>,
    val ageRange: AgeRange,
    val runningTime: String,
    val locateAddress: String,
    val distance: Float,
    val latitude: Double,
    val longitude: Double,
    val meetingDate: String,
    val bookmarkCount: Int,
    val runningType: RunningItemType,
    val finish: Boolean,
)
