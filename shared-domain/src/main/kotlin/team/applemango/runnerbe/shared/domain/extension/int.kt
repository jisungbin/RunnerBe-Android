/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [int.kt] created by Ji Sungbin on 22. 3. 1. 오후 2:37
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

import team.applemango.runnerbe.shared.domain.notAllowedValueMessage

fun Int.toBoolean() = when (this) {
    0 -> false
    1 -> true
    else -> throw IllegalStateException(notAllowedValueMessage(this))
}
