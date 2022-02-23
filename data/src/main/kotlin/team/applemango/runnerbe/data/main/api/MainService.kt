/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainService.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import team.applemango.runnerbe.data.main.model.RunningItemResponse

interface MainService {
    @GET("/users/main/{runningTag}")
    suspend fun requestRunningItems(
        @Query("whetherEnd") includeEndItems: Boolean,
        @Query("filter") mainFilterType: String,
        @Query("distanceFilter") distanceFilter: String,
        @Query("genderFilter") genderFilter: String,
        @Query("ageFilterMax") maxAgeFilter: String,
        @Query("ageFilterMin") minAgeFilter: String,
        @Query("jobFilter") jobFilter: String,
        @Query("userLongitude") longitude: Float,
        @Query("userLatitude") latitude: Float,
        @Query("keywordSearch") keyword: String,
    ): Response<RunningItemResponse>
}
