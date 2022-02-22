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

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    runOnUiThread {
        toastBuilder(context = applicationContext, message = message, length = length)
    }
}

fun toast(context: Context, message: Context.() -> String, length: Int = Toast.LENGTH_SHORT) {
    if (context is Activity) {
        context.toast(message(context), length)
    } else {
        toastBuilder(context, message(context), length)
    }
}

fun toast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
    if (context is Activity) {
        context.toast(message, length)
    } else {
        toastBuilder(context, message, length)
    }
}

private fun toastBuilder(context: Context, message: String, length: Int) {
    Toast.makeText(context, message, length).show()
}
