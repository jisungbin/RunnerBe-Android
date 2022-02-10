/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import team.applemango.runnerbe.data.login.mapper.toBoolean
import team.applemango.runnerbe.data.util.extension.requireSuccessfulBody
import team.applemango.runnerbe.data.util.registerApi
import team.applemango.runnerbe.domain.login.model.User
import team.applemango.runnerbe.domain.login.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun checkUsableEmail(email: String): Boolean {
        return registerApi.checkUsableEmail(email)
            .requireSuccessfulBody("checkUsableEmail")
            .toBoolean()
    }

    override suspend fun register(user: User) {
        TODO("Not yet implemented")
    }
}