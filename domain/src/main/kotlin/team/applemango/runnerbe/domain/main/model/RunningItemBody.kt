/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemBody.kt] created by Ji Sungbin on 22. 2. 24. 오후 7:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model

import java.util.Date
import team.applemango.runnerbe.domain.main.constant.load.AgeRange
import team.applemango.runnerbe.domain.main.constant.load.GenderFilter
import team.applemango.runnerbe.domain.main.constant.load.RunningItemType

/**
 * API Call Body entry model
 *
 * @property title 아이템 제목
 * @property itemType 아이템 타입
 * @property meetingDate 모임 시간
 * @property runningTime 예정 러닝 소요 시간
 * @property locate 러닝 모임 장소
 * @property ageRange 러닝 참여 가능 연령대
 * @property maxPeopleCount 러닝 참여 가능 최대 인원 수
 * @property gender 러닝 참여 가능 성별
 * @property message 아이템 메시지
 */
data class RunningItemBody(
    val title: String,
    val itemType: RunningItemType,
    val meetingDate: Date,
    val runningTime: String,
    val locate: Locate,
    val ageRange: AgeRange,
    val maxPeopleCount: Int,
    val gender: GenderFilter,
    val message: String,
)
