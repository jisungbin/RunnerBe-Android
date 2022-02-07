/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginService.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.api

import retrofit2.Response
import retrofit2.http.POST
import team.applemango.runnerbe.data.login.model.LoginRequestResponse

interface LoginService {
    @POST("/users/kakao-login")
    suspend fun requestKakaoLogin(): Response<LoginRequestResponse>
}
