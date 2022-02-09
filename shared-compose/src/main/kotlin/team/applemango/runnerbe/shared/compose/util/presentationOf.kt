/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [presentationOf.kt] created by Ji Sungbin on 22. 2. 8. 오후 11:05
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util

import android.app.Activity

fun Activity.presentationDrawableOf(name: String) = getDrawable(
    resources.getIdentifier(
        name,
        "drawable",
        "team.applemango.runnerbe"
    )
) ?: throw Exception("$name resource is unavailable.")

fun Activity.presentationStringOf(name: String) = getString(
    resources.getIdentifier(
        name,
        "string",
        "team.applemango.runnerbe"
    )
)
