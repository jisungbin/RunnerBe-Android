/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardActivity.kt] created by Ji Sungbin on 22. 2. 9. 오전 1:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.ProvideWindowInsets
import team.applemango.runnerbe.component.StateIcon
import team.applemango.runnerbe.constant.ScreenType
import team.applemango.runnerbe.shared.base.WindowInsetActivity

class MainActivity : WindowInsetActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen(modifier: Modifier) {
        val bottomBarStateIcons by remember {
            listOf(
                StateIcon(id = ScreenType.Main, )
            )
        }

        ConstraintLayout(modifier = modifier) {
            val (bottomBar, content) = createRefs()

        }
    }
}
