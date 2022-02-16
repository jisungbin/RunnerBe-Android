/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [flow.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable

fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    action: suspend CoroutineScope.(T) -> Unit,
) {
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = Lifecycle.State.CREATED
        ).cancellable().collect { value ->
            action(value)
        }
    }
}
