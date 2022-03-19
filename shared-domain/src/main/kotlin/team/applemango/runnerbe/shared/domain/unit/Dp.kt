/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Dp.kt] created by Ji Sungbin on 22. 3. 18. 오후 9:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.unit

@JvmInline
value class Dp(val value: Float)

// The adding of 0.5 is used to round UP to the nearest integer value.
fun Dp.toInt() = (value + 0.5f).toInt()
