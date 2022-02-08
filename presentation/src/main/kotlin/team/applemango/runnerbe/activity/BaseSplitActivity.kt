/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BaseSplitActivity.kt] created by Ji Sungbin on 22. 2. 8. 오후 5:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.content.Context
import androidx.activity.ComponentActivity
import com.google.android.play.core.splitcompat.SplitCompat

abstract class BaseSplitActivity : ComponentActivity() {
    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        SplitCompat.installActivity(this)
    }
}
