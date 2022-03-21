/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [local.kt] created by Ji Sungbin on 22. 3. 21. 오후 1:45
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.extension

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi

@LocalActivityUsageApi
val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    noLocalProvidedFor("LocalActivity")
}

@Suppress("SameParameterValue")
private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
