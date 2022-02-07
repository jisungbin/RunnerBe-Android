/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepository.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.repository

import team.applemango.runnerbe.domain.model.User

interface LoginRepository {
    suspend fun requestKakao(): User
    suspend fun requestNaver(): User
}
