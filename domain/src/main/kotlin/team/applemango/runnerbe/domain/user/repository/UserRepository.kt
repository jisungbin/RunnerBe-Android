/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRepository.kt] created by Ji Sungbin on 22. 2. 28. 오후 6:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.repository

interface UserRepository {
    /**
     * 닉네임 설정 (5번 API)
     */
    suspend fun setNickname()

    /**
     * 찜 관리 (등록/해제) (20번 API)
     */
    suspend fun updateBookmarkItem()

    /**
     * 찜 목록 조회 (21번 API)
     */
    suspend fun getBookmarkItems()

    /**
     * 프로필 사진 변경 (22번 API)
     */
    suspend fun updateProfileImage()

    /**
     * 직군 변경 (23번 API)
     */
    suspend fun checkJob()

    /**
     * 마이페이지 정보 조회 (24번 API)
     */
    suspend fun loadMyPage()

    /**
     * 푸시 알림 요청 (26번 API)
     */
    suspend fun pushNotification()

    /**
     * 출석 (27번 API)
     */
    suspend fun attendanceCheck()
}