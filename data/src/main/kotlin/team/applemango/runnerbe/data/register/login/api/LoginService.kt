/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginService.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import team.applemango.runnerbe.data.register.login.model.login.LoginRequestResponse
import team.applemango.runnerbe.domain.register.login.model.AccessToken

internal interface LoginService {
    @POST("/users/{platformName}-login")
    suspend fun request(
        @Path("platformName") platformName: String,
        @Body accessToken: AccessToken,
    ): Response<LoginRequestResponse>
}
