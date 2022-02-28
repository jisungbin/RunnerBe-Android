/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.repository

import team.applemango.runnerbe.data.register.login.mapper.toBoolean
import team.applemango.runnerbe.data.register.login.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.loginApi
import team.applemango.runnerbe.data.util.registerApi
import team.applemango.runnerbe.domain.register.login.constant.UserRegisterResult
import team.applemango.runnerbe.domain.register.login.model.AccessToken
import team.applemango.runnerbe.domain.register.login.model.UserRegister
import team.applemango.runnerbe.domain.register.login.model.UserToken
import team.applemango.runnerbe.domain.register.login.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun login(platformName: String, accessToken: AccessToken): UserToken {
        val request = loginApi.request(
            platformName = platformName,
            accessToken = accessToken
        )
        return request.requireSuccessfulBody(
            requestName = "loginApi.request $platformName",
            resultVerifyBuilder = { body ->
                body.isSuccess == true && body.code in listOf(1001, 1007) && body.loginResult != null
            }
        ).toDomain()
    }

    override suspend fun checkUsableEmail(email: String): Boolean {
        return registerApi.checkUsableEmail(email)
            .requireSuccessfulBody(
                requestName = "registerApi.checkUsableEmail",
                resultVerifyBuilder = { body ->
                    body.isSuccess != null // receive only isSuccess field
                }
            )
            .toBoolean()
    }

    override suspend fun register(user: UserRegister): UserRegisterResult {
        return registerApi.requestRegister(user)
            .requireSuccessfulBody(
                requestName = "registerApi.requestRegister",
                resultVerifyBuilder = { body ->
                    body.isSuccess == true && body.jwt != null
                }
            )
            .toDomain()
    }
}
