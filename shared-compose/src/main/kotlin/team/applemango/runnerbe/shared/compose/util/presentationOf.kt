/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [presentationOf.kt] created by Ji Sungbin on 22. 2. 8. 오후 11:05
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.util

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

private const val PresentationPackage = "team.applemango.runnerbe"

@Composable
fun presentationDrawableOf(name: String) = (LocalContext.current as Activity).run {
    getDrawable(
        resources.getIdentifier(
            name,
            "drawable",
            PresentationPackage
        )
    ) ?: throw Exception("$name resource is unavailable.")
}

@Composable
fun presentationStringOf(name: String) = (LocalContext.current as Activity).run {
    getString(
        resources.getIdentifier(
            name,
            "string",
            PresentationPackage
        )
    )
}

fun presentationColorOf(context: Context, name: String) = ContextCompat.getColor(
    context,
    context.resources.getIdentifier(
        name,
        "color",
        PresentationPackage
    )
)
