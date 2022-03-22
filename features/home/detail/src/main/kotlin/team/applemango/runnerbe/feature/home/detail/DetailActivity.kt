/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DetailActivity.kt] created by Ji Sungbin on 22. 3. 22. 오후 10:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import team.applemango.runnerbe.shared.android.extension.finishWithAnimation
import team.applemango.runnerbe.shared.android.extension.setWindowInsets
import team.applemango.runnerbe.shared.android.extension.toast
import team.applemango.runnerbe.shared.domain.constant.Intent

class DetailActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val runningItemId = intent.getIntExtra(Intent.Home.RunningItemId, -1)
        if (runningItemId == -1) {
            toast(getString(R.string.detail_toast_fail_load_item))
            finishWithAnimation()
            return
        }

        setWindowInsets()
        setContent {

        }
    }

}
