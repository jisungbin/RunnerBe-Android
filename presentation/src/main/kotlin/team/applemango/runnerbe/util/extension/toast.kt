package team.applemango.runnerbe.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.toast(message: String) {
    toast(this) { message }
}

fun toast(context: Context, message: Context.() -> String) {
    Toast.makeText(context, message(context), Toast.LENGTH_SHORT).show()
}
