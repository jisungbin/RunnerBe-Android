/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterService.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:51
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import team.applemango.runnerbe.data.login.model.CheckDuplicateEmailResponse
import team.applemango.runnerbe.data.login.model.UserRegisterResponse
import team.applemango.runnerbe.domain.login.model.UserRegister

internal interface RegisterService {
    @GET("/users/email/check/{officeEmail}")
    suspend fun checkUsableEmail(
        @Path("officeEmail") email: String,
    ): Response<CheckDuplicateEmailResponse>

    @POST("/users")
    suspend fun requestRegister(
        @Body user: UserRegister,
    ): Response<UserRegisterResponse>
}
