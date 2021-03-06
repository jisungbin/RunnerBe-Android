/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [User.kt] created by Ji Sungbin on 22. 2. 7. 오후 6:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.model

data class UserToken(
    val userId: Int? = null,
    val jwt: String? = null,
    val uuid: String? = null,
) {
    val isRegisteredUser = jwt != null
}
