/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [activity.kt] created by Ji Sungbin on 22. 2. 9. 오후 5:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.extension

import android.app.Activity
import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import team.applemango.runnerbe.shared.domain.extension.toMessage

inline fun <reified T : Activity> Activity.changeActivityWithAnimation() {
    startActivity(Intent(this, T::class.java))
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    finish()
}

fun Activity.finishWithAnimation() {
    finish()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

fun Activity.setWindowInsetsUsage() {
    window.setFlags( // 네비게이션바까지 영역 확장하려면 필요
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Activity.basicExceptionHandler(exception: Throwable) {
    toast(
        message = exception.toMessage(),
        length = Toast.LENGTH_LONG
    )
    logeukes(type = LoggerType.E) { exception }
}
