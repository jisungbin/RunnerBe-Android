/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWrtieActivity.kt] created by Ji Sungbin on 22. 3. 18. 오전 6:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.shared.base.WindowInsetsActivity

@AndroidEntryPoint
class RunningItemWriteActivity : WindowInsetsActivity() {

    private val vm: RunningItemWriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
        }
    }
}
