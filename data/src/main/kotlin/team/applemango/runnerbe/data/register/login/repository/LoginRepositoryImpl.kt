/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.repository

import team.applemango.runnerbe.data.register.login.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.loginApi
import team.applemango.runnerbe.domain.register.login.model.AccessToken
import team.applemango.runnerbe.domain.register.login.model.UserToken
import team.applemango.runnerbe.domain.register.login.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override suspend fun request(platformName: String, accessToken: AccessToken): UserToken {
        val request = loginApi.request(
            platformName = platformName,
            accessToken = accessToken
        )
        return request.requireSuccessfulBody(
            requestName = platformName,
            resultVerifyBuilder = { body ->
                body.isSuccess == true && body.code in 1001..1002 && body.loginResult != null
            }
        ).toDomain()
    }
}
