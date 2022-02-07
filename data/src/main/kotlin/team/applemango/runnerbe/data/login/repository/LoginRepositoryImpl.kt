/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import team.applemango.runnerbe.data.login.model.AccessToken
import team.applemango.runnerbe.data.util.loginApi
import team.applemango.runnerbe.domain.login.model.User
import team.applemango.runnerbe.domain.login.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override suspend fun requestKakao(accessToken: String): User {
        val request = loginApi.requestKakao(AccessToken(accessToken))
        return request.requireResponse("kakao").toDomain()
    }

    override suspend fun requestNaver(accessToken: String): User {
        val request = loginApi.requestNaver(AccessToken(accessToken))
        return request.requireResponse("naver").toDomain()
    }
}
