/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MyPageInformationResponseMapper.kt] created by Ji Sungbin on 22. 3. 1. 오후 3:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.mapper

import team.applemango.runnerbe.data.runningitem.mapper.MappingType
import team.applemango.runnerbe.data.runningitem.mapper.toDomain
import team.applemango.runnerbe.data.user.model.MyPageInformationResponse
import team.applemango.runnerbe.domain.user.model.MyPageInformation
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage

internal fun MyPageInformationResponse.toDomain(): MyPageInformation {
    requireNotNull(result) {
        requireFieldNullMessage("result")
    }
    return MyPageInformation(
        me = result.myInfo?.first()?.toDomain() ?: throw IllegalStateException(
            requireFieldNullMessage("myInfo")
        ),
        ownRunningItems = result.myPosting?.map { item ->
            checkNotNull(item) {
                requireFieldNullMessage("myPosting item")
            }
            item.toDomain(MappingType.MyPageOwnRunningItemFields)
        } ?: throw IllegalStateException(requireFieldNullMessage("myPosting")),
        joinRunnings = result.myRunning?.map { item ->
            checkNotNull(item) {
                requireFieldNullMessage("myRunning item")
            }
            item.toDomain(MappingType.MyPageJoinRunningItemFields)
        } ?: throw IllegalStateException(requireFieldNullMessage("myRunning"))
    )
}
