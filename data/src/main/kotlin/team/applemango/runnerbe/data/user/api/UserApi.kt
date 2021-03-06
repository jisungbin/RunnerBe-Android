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
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.applemango.runnerbe.data.common.DefaultResponse
import team.applemango.runnerbe.data.user.model.BookmarkItemsResponse
import team.applemango.runnerbe.data.user.model.MyPageInformationResponse
import team.applemango.runnerbe.domain.user.model.wrapper.JobWrapper
import team.applemango.runnerbe.domain.user.model.wrapper.NicknameWrapper
import team.applemango.runnerbe.domain.user.model.wrapper.ProfileImageUrlWrapper

interface UserApi {
    @PATCH("/users/{userId}/name")
    fun setNickname(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
        @Body nickname: NicknameWrapper,
    ): Response<DefaultResponse>

    @POST("/users/{userId}/bookmarks/{whetherAdd}")
    fun updateBookmarkItem(
        @Header("x-access-token") jwt: String,
        @Query("postId") postId: Int,
        @Path("userId") userId: Int,
        @Path("whetherAdd") whetherAdd: String,
    ): Response<DefaultResponse>

    @GET("/users/{userId}/bookmarks")
    fun loadBookmarkItems(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
    ): Response<BookmarkItemsResponse>

    @PATCH("/users/{userId}/profileImage")
    fun updateProfileImage(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
        @Body profileImageUrl: ProfileImageUrlWrapper,
    ): Response<DefaultResponse>

    @PATCH("/users/{userId}/job")
    fun changeJob(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
        @Body jobCode: JobWrapper,
    ): Response<DefaultResponse>

    @GET("/users/{userId}/myPage")
    fun loadMyPage(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
    ): Response<MyPageInformationResponse>

    @PATCH("/runnings/{postId}/attendees/{userId}")
    fun attendanceCheck(
        @Header("x-access-token") jwt: String,
        @Path("postId") postId: Int,
        @Path("userId") userId: Int,
    ): Response<DefaultResponse>
}
