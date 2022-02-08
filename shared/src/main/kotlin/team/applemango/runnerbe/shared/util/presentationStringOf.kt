/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [presentationStringOf.kt] created by Ji Sungbin on 22. 2. 8. 오후 6:34
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util

import android.app.Activity

fun Activity.presentationStringOf(name: String) = getString(
    resources.getIdentifier(
        name,
        "string",
        "team.applemango.runnerbe"
    )
)
