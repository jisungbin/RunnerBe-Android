/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BookmarkItemsResponseMapper.kt] created by Ji Sungbin on 22. 3. 1. 오전 10:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.mapper

import team.applemango.runnerbe.data.runningitem.mapper.MappingType
import team.applemango.runnerbe.data.runningitem.mapper.toDomain
import team.applemango.runnerbe.data.user.model.BookmarkItemsResponse
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage

internal fun BookmarkItemsResponse.toDomain() = requireNotNull(result) {
    requireFieldNullMessage("result")
}.bookMarkList?.map { item ->
    checkNotNull(item) {
        requireFieldNullMessage("result")
    }
    item.toDomain(MappingType.BookmarkApiFields)
} ?: throw IllegalStateException(requireFieldNullMessage("bookMarkList"))
