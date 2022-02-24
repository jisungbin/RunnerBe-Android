/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ResultItem.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model.runningitem

import team.applemango.runnerbe.domain.main.common.RunningItemType
import team.applemango.runnerbe.domain.main.filter.AgeFilter
import team.applemango.runnerbe.domain.main.model.common.Locate
import team.applemango.runnerbe.domain.runner.Gender
import team.applemango.runnerbe.domain.runner.Job

/**
 * @property itemId 게시글 아이디
 * @property ownerId 게시글 작성자 아이디
 * @property ownerNickName 게시글 작성자 닉네임
 * @property ownerProfileImageUrl 게시글 작성자 프로필 사진 URL
 * @property createdAt 게시글 작성 날짜
 * @property bookmarkCount 게시글 북마크 개수
 * @property runningType 게시글 아이템 타입 (출근 전, 퇴근 후, 휴일)
 * @property finish 게시글 러닝 모집 마감 여부
 * @property maxRunnerCount 최대 러닝 인원
 * @property title 게시글 제목
 * @property gender 러닝 참여 가능 성별 필터
 * @property jobs 러닝 참여자 직업 리스트
 * @property ageFilter 러닝 참여 가능 연령대 필터
 * @property runningTime 예정 러닝 소요 시간
 * @property locate 모임 장소의 위치(위도, 경도, 주소)
 * @property distance 모임 장소와 내 위치간 거리 (단위: KM)
 * @property meetingDate 모임 날짜 및 시간 (ex_05/22(일) AM11:22)
 * @property message 게시글 메시지
 *
 * 총 18개 필드
 */
data class RunningItem(
    val itemId: Int,
    val ownerId: Int,
    val ownerNickName: String,
    val ownerProfileImageUrl: String,
    val createdAt: String,
    val bookmarkCount: Int,
    val runningType: RunningItemType,
    val finish: Boolean,
    val maxRunnerCount: Int,
    val title: String,
    val gender: Gender,
    val jobs: List<Job>,
    val ageFilter: AgeFilter,
    val runningTime: String,
    val locate: Locate,
    val distance: Float,
    val meetingDate: String,
    val message: String,
)
