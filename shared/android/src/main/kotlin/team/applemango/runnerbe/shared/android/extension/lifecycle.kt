/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [lifecycle.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

fun LifecycleOwner.launchedWhenCreated(action: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated {
        action()
    }
}
