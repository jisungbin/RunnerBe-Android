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
import team.applemango.runnerbe.data.util.registerApi
import team.applemango.runnerbe.domain.register.login.constant.UserRegisterResult
import team.applemango.runnerbe.domain.register.login.model.AccessToken
import team.applemango.runnerbe.domain.register.login.model.UserRegister
import team.applemango.runnerbe.domain.register.login.model.UserToken
import team.applemango.runnerbe.domain.register.login.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun login(platformName: String, accessToken: AccessToken): UserToken {
        val request = registerApi.login(
            platformName = platformName,
            accessToken = accessToken
        )
        return request.requireSuccessfulBody(
            requestName = "registerApi.login $platformName",
            resultVerifyBuilder = { body ->
                body.code in listOf(1001, 1007) && body.loginResult != null
            }
        ).toDomain()
    }

    override suspend fun checkUsableEmail(email: String): Boolean {
        return registerApi.checkUsableEmail(email)
            .requireSuccessfulBody(
                requestName = "registerApi.checkUsableEmail",
                checkBodyIsSuccess = false,
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
                    body.jwt != null && body.code in listOf(1005, 1006, 3001, 3002, 3004)
                }
            )
            .toDomain()
    }
}
