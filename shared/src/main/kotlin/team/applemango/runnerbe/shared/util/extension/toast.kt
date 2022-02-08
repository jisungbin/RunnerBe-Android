/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [toast.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.toast(message: String) {
    runOnUiThread {
        toast(applicationContext) { message }
    }
}

fun toast(context: Context, message: Context.() -> String) {
    if (context is Activity) {
        context.toast(message(context))
    } else {
        Toast.makeText(context, message(context), Toast.LENGTH_SHORT).show()
    }
}

fun toast(context: Context, message: String) {
    if (context is Activity) {
        context.toast(message)
    } else {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
