/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [activity.kt] created by Ji Sungbin on 22. 2. 9. 오후 5:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util.extension

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.changeActivityWithAnimation() {
    startActivity(Intent(this, T::class.java))
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    finish()
}
