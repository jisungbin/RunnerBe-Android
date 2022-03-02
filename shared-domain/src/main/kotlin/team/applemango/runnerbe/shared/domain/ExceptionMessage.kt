/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ExceptionMessage.kt] created by Ji Sungbin on 22. 2. 24. 오후 3:21
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain

fun requireFieldNullMessage(fieldName: String) = "Require `$fieldName` field is null."
fun notAllowedValueMessage(value: Any) = "$value is not allowed here."
fun unknownResultMessage(result: Any) = "Unknown result: $result"
