/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [result.kt] created by Ji Sungbin on 22. 2. 7. 오후 6:43
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.extension

internal fun <T> success(value: T) = Result.success(value)
internal fun <T> failure(message: String) = Result.failure<T>(Exception(message))
internal fun <T> failure(exception: Throwable) = Result.failure<T>(exception)
