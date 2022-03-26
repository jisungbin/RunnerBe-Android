/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainRepository.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:51
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.repository

import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBodyData
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation

interface RunningItemRepository {
    /**
     * 러닝 아이템 작성 (6번 API)
     *
     * @return 인증 전 유저도 러닝 아이템 작성은 요청할 수 있으니
     * `아직 인증되지 않음` 상태를 포함한 [BaseResult] 를 리턴함
     */
    suspend fun write(
        jwt: String,
        userId: Int,
        item: RunningItemApiBodyData,
    ): BaseResult

    /**
     * 러닝 아이템 리스트 조회 (7번 API)
     */
    suspend fun loadItems(
        itemType: String,
        includeEndItems: String,
        itemFilter: String,
        distance: String,
        gender: String,
        minAge: String,
        maxAge: String,
        job: String,
        latitude: Float,
        longitude: Float,
        keyword: String,
    ): List<RunningItem>

    /**
     * 러닝 아이템 상세 정보 조회 (8번 API)
     *
     * @return 사원증 인증이 된 유저라면 [RunningItemInformation] 을 반환하고,
     * 그렇지 않은 유저라면 null 을 반환함
     */
    suspend fun loadDetail(
        jwt: String,
        postId: Int,
        userId: Int,
    ): RunningItemInformation?

    /**
     * 러너 모집 마감 (러닝 아이템 작성자 전용, 10번 API)
     *
     * @return 러너 모집을 마감하기 위해선 러닝 아이템을 작성해야 함
     * 러닝 아이템을 작성하는건 인증된 회원만 가능하므로 이 API(러너 모집 마감)을
     * 호출할 수 있는 상태는 무조건 유저가 인증이 된 상태임
     * 따라서 마감 성공 여부를 나타내는 [Boolean] 값만 리턴함
     */
    suspend fun finish(
        jwt: String,
        postId: Int,
    ): Boolean

    /**
     * 러닝 아이템 수정 (11번 API)
     *
     * @return 러닝 아이템을 수정하기 위해선 러닝 아이템을 작성해야 함
     * 러닝 아이템을 작성하는건 인증된 회원만 가능하므로 이 API(러닝 아이템 수정)을
     * 호출할 수 있는 상태는 무조건 유저가 인증이 된 상태임
     * 따라서 수정 성공 여부를 나타내는 [Boolean] 값만 리턴함
     */
    suspend fun edit(
        jwt: String,
        postId: Int,
        userId: Int,
        item: RunningItemApiBodyData,
    ): Boolean

    /**
     * 러닝 아이템 삭제 (12번 API)
     *
     * @return 러닝 아이템을 삭제하기 위해선 러닝 아이템을 작성해야 함
     * 러닝 아이템을 작성하는건 인증된 회원만 가능하므로 이 API(러닝 아이템 삭제)을
     * 호출할 수 있는 상태는 무조건 유저가 인증이 된 상태임
     * 따라서 삭제 성공 여부를 나타내는 [Boolean] 값만 리턴함
     */
    suspend fun delete(
        jwt: String,
        postId: Int,
        userId: Int,
    ): Boolean

    /**
     * 러닝 참여 신청 (러닝 아이템 작성자는 불가능, 18번 API)
     *
     * @return 인증 전 유저도 참여 신청은 할 수 있으니
     * `아직 인증되지 않음` 상태를 포함한 [BaseResult] 를 리턴함
     * 이전에 신청한 기록이 없을 때만 이 API 를 호출하는 버튼이
     * 활성화 됨으로 중복 신청은 가능하지 않음
     */
    suspend fun requestJoin(
        jwt: String,
        postId: Int,
        userId: Int,
    ): BaseResult

    /**
     * 러닝 참여 신청 관리 (러닝 아이템 작성자 전용, 19번 API)
     *
     * @param jwt 인증된 유저의 JWT
     * @param postId 러닝 아이템 아이디
     * @param userId 인증된 유저의 아이디
     * @param runnerId 러닝 참여를 신청한 유저의 아이디
     * @param state 참여 신청 관리 값 (Y: 수락, D: 거절)
     *
     * @return 러닝 참여 신청 관리를 하기 위해선 러닝 아이템을 작성해야 함
     * 러닝 아이템을 작성하는건 인증된 회원만 가능하므로 이 API(러닝 참여 신청 관리)을
     * 호출할 수 있는 상태는 무조건 유저가 인증이 된 상태임
     * 따라서 러닝 참여 신청 관리 성공 여부를 나타내는 [Boolean] 값만 리턴함
     */
    suspend fun joinManage(
        jwt: String,
        postId: Int,
        userId: Int,
        runnerId: Int,
        state: String,
    ): Boolean

    /**
     * 러닝 아이템 신고 (25번 API)
     *
     * @return 신고는 모든 회원이 가능해야 하므로
     * 신고 성공 여부를 나타내는 [Boolean] 값만 리턴함
     */
    suspend fun report(
        jwt: String,
        postId: Int,
        userId: Int,
    ): Boolean
}
