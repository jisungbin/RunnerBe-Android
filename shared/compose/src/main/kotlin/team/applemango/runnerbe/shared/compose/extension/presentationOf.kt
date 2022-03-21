/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [presentationOf.kt] created by Ji Sungbin on 22. 2. 8. 오후 11:05
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.extension

import android.content.res.Resources
import androidx.compose.runtime.Composable
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi

private const val PresentationPackage = "team.applemango.runnerbe"

@LocalActivityUsageApi
@Composable
fun presentationDrawableOf(name: String) = LocalActivity.current.run {
    getDrawable(
        resources.getIdentifier(
            name,
            "drawable",
            PresentationPackage
        )
    ) ?: throw Resources.NotFoundException("$name resource is unavailable.")
}

@LocalActivityUsageApi
@Composable
fun presentationStringOf(name: String) = LocalActivity.current.run {
    getString(
        resources.getIdentifier(
            name,
            "string",
            PresentationPackage
        )
    )
}
