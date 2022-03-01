/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRepository.kt] created by Ji Sungbin on 22. 2. 28. 오후 6:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.repository

import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.user.constant.JobChangeResult
import team.applemango.runnerbe.domain.user.constant.NicknameChangeResult
import team.applemango.runnerbe.domain.user.model.wrapper.JobWrapper
import team.applemango.runnerbe.domain.user.model.wrapper.NicknameWrapper
import team.applemango.runnerbe.domain.user.model.wrapper.ProfileImageUrlWrapper

interface UserRepository {
    /**
     * 닉네임 설정 (5번 API)
     *
     * @return 닉네임 설정 성공 여부
     * return type 은 BaseResult 이지만 [NicknameChangeResult] 가 return 될 수 있음
     */
    suspend fun setNickname(
        jwt: String,
        userId: Int,
        nickName: NicknameWrapper,
    ): BaseResult

    /**
     * 찜 관리 (등록/해제) (20번 API)
     *
     * @param jwt 유저 JWT
     * @param postId 북마크 할 러닝 아이템의 아이디
     * @param userId 유저 아이디
     * @param whetherAdd 북마크 상태 (Y: 등록 / N: 해제)
     *
     * @return 북마크 업데이트 반영 여부
     */
    suspend fun updateBookmarkItem(
        jwt: String,
        postId: Int,
        userId: Int,
        whetherAdd: String,
    ): BaseResult

    /**
     * 찜 목록 조회 (21번 API)
     */
    suspend fun loadBookmarkItems(
        jwt: String,
        userId: Int,
    ): List<RunningItem>

    /**
     * 프로필 사진 변경 (22번 API)
     */
    suspend fun updateProfileImage(
        jwt: String,
        userId: Int,
        profileImageUrl: ProfileImageUrlWrapper,
    ): BaseResult

    /**
     * 직군 변경 (23번 API)
     *
     * @return 닉네임 설정 성공 여부
     * return type 은 BaseResult 이지만 [JobChangeResult] 가 return 될 수 있음
     */
    suspend fun changeJob(
        jwt: String,
        userId: Int,
        jobCode: JobWrapper,
    ): BaseResult

    /**
     * 마이페이지 정보 조회 (24번 API)
     */
    suspend fun loadMyPage()

    /**
     * 출석 (27번 API)
     */
    suspend fun attendanceCheck()
}