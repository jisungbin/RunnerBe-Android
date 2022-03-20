/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [flow.kt] created by Ji Sungbin on 22. 3. 21. 오전 12:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.defaultCatch(
    name: String? = null,
    action: (exception: IllegalStateException) -> Unit
) = catch { cause ->
    val exception =
        IllegalStateException("${name ?: cause.tag} flow collect exception: ${cause.message}")
    action(exception)
}
