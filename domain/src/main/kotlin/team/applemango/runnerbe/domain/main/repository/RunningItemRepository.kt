/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainRepository.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:51
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.repository

import team.applemango.runnerbe.domain.main.common.BaseResult
import team.applemango.runnerbe.domain.main.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.main.model.runningitem.RunningItemApiBodyData
import team.applemango.runnerbe.domain.main.model.runningitem.information.RunningItemInformation

interface RunningItemRepository {
    /**
     * 러닝 아이템 리스트 조회 (7번 API)
     */
    suspend fun loadRunningItems(
        itemType: String,
        includeEndItems: Boolean,
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
     * 러닝 아이템 작성 (6번 API)
     */
    suspend fun writeRunningItem(
        jwt: String,
        userId: Int,
        item: RunningItemApiBodyData,
    ): BaseResult

    /**
     * 러닝 아이템 상세 정보 조회 (8번 API)
     */
    suspend fun getRunningItemInformation(
        jwt: String,
        userId: Int,
        postId: Int,
    ): RunningItemInformation
}
