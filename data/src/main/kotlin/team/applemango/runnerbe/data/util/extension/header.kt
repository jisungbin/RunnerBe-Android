/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [header.kt] created by Ji Sungbin on 22. 2. 24. 오후 7:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.extension

internal fun String.toXAccessTokenHeader() = mapOf("x-access-token" to this)
