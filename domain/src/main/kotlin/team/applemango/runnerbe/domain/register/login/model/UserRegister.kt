/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterUser.kt] created by Ji Sungbin on 22. 2. 12. 오후 2:22
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.login.model

import kotlin.random.Random

data class UserRegister(
    val uuid: String,
    val nickName: String = "러너비${Random.nextInt(10_000, 1_000_000)}",
    val birthday: Int,
    val gender: String,
    val job: String,
    val officeEmail: String?,
    val idCardImageUrl: String?,
) {
    val isVerifyWithEmployeeId = idCardImageUrl != null
}
