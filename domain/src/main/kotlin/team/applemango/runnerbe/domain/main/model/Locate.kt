/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Locate.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:17
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.model

data class Locate(
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
