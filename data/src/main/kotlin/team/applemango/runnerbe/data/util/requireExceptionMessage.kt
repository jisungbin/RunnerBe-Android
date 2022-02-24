/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [requireExceptionMessage.kt] created by Ji Sungbin on 22. 2. 24. 오후 3:21
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util

fun requireFieldExceptionMessage(fieldName: String) = "Require `$fieldName` field is null."
fun requireValueExceptionMessage(valueName: String) = "Require `$valueName` value is null."
