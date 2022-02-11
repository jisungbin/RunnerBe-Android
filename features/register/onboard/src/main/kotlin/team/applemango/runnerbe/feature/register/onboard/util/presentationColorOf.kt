/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [presentationColorOf.kt] created by Ji Sungbin on 22. 2. 12. 오전 4:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.util

import android.content.Context
import androidx.core.content.ContextCompat

private const val PresentationPackage = "team.applemango.runnerbe"

internal fun presentationColorOf(context: Context, name: String) = ContextCompat.getColor(
    context,
    context.resources.getIdentifier(
        name,
        "color",
        PresentationPackage
    )
)
