/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserApi.kt] created by Ji Sungbin on 22. 2. 28. 오후 11:33
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.applemango.runnerbe.data.common.DefaultResponse
import team.applemango.runnerbe.domain.user.model.Nickname

interface UserApi {
    @PATCH("/users/{userId}/name")
    fun setNickname(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
        @Body nickname: Nickname,
    ): Response<DefaultResponse>

    @POST("/users/{userId}/bookmarks/{whetherAdd}")
    fun updateBookmarkItem(
        @Header("x-access-token") jwt: String,
        @Query("postId") postId: Int,
        @Path("userId") userId: Int,
        @Path("whetherAdd") whetherAdd: String,
    ): Response<DefaultResponse>
}