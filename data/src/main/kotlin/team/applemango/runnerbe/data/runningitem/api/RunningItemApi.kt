/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainService.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.applemango.runnerbe.data.runningitem.model.DefaultResponse
import team.applemango.runnerbe.data.runningitem.model.runningitem.RunningItemsResponse
import team.applemango.runnerbe.data.runningitem.model.runningitem.information.RunningItemInformationResponse
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBodyData

interface RunningItemApi {
    @GET("/users/main/{runningTag}")
    suspend fun loadRunningItems(
        @Path("runningTag") itemType: String,
        @Query("whetherEnd") includeEndItems: Boolean,
        @Query("filter") itemFilter: String,
        @Query("distanceFilter") distance: String,
        @Query("genderFilter") gender: String,
        @Query("ageFilterMin") minAge: String,
        @Query("ageFilterMax") maxAge: String,
        @Query("jobFilter") job: String,
        @Query("userLatitude") latitude: Float,
        @Query("userLongitude") longitude: Float,
        @Query("keywordSearch") keyword: String,
    ): Response<RunningItemsResponse>

    @POST("/postings/{userId}")
    suspend fun writeRunningItem(
        @Header("x-access-token") jwt: String,
        @Path("userId") userId: Int,
        @Body item: RunningItemApiBodyData,
    ): Response<DefaultResponse>

    @GET("/postings/{postId}/{userId}")
    suspend fun getRunningItemInformation(
        @Header("x-access-token") jwt: String,
        @Path("postId") postId: Int,
        @Path("userId") userId: Int,
    ): Response<RunningItemInformationResponse>
}