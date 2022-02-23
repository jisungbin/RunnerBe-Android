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
import team.applemango.runnerbe.data.register.login.mapper.toResultDomain
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.registerApi
import team.applemango.runnerbe.domain.login.model.UserRegister
import team.applemango.runnerbe.domain.login.model.result.UserRegisterResult
import team.applemango.runnerbe.domain.login.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun checkUsableEmail(email: String): Boolean {
        return registerApi.checkUsableEmail(email)
            .requireSuccessfulBody("checkUsableEmail")
            .toBoolean()
    }

    override suspend fun register(user: UserRegister): UserRegisterResult {
        return registerApi.requestRegister(user)
            .requireSuccessfulBody("register")
            .toResultDomain()
    }
}
