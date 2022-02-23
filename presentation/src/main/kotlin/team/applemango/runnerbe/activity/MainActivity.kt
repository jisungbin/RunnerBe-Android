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
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import team.applemango.runnerbe.R
import team.applemango.runnerbe.component.IconBottomBar
import team.applemango.runnerbe.component.StateIcon
import team.applemango.runnerbe.constant.ScreenType
import team.applemango.runnerbe.shared.base.WindowInsetActivity
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset

class MainActivity : WindowInsetActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setStatusBarColor(Color.Transparent)
                    systemUiController.setNavigationBarColor(ColorAsset.G6)
                }
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.BlackGradientBrush)
                        .systemBarsPadding(start = false, end = false)
                )
            }
        }
    }

    @Composable
    private fun MainScreen(modifier: Modifier) {
        val bottomBarStateIcons = remember {
            listOf(
                StateIcon(
                    id = ScreenType.Main,
                    activate = R.drawable.ic_round_home_24,
                    inactivate = R.drawable.ic_outlined_home_24
                ),
                StateIcon(
                    id = ScreenType.Bookmark,
                    activate = R.drawable.ic_round_bookmark_24,
                    inactivate = R.drawable.ic_outlined_bookmark_24
                ),
                StateIcon(
                    id = ScreenType.Mypage,
                    activate = R.drawable.ic_round_me_24,
                    inactivate = R.drawable.ic_outlined_me_24
                )
            )
        }
        var selectedScreenType by remember { mutableStateOf(bottomBarStateIcons.first().id) }

        ConstraintLayout(modifier = modifier) {
            val (content, bottomBars) = createRefs()

            Crossfade(
                modifier = Modifier.constrainAs(content) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomBars.top)
                },
                targetState = selectedScreenType
            ) { screen ->
                when (screen) {
                    ScreenType.Main -> {
                        Text(text = "1")
                    }
                    ScreenType.Bookmark -> {
                        Text(text = "2")
                    }
                    ScreenType.Mail -> throw NotImplementedError()
                    ScreenType.Mypage -> {
                        Text(text = "3")
                    }
                }
            }
            IconBottomBar(
                modifier = Modifier.constrainAs(bottomBars) {
                    bottom.linkTo(parent.bottom)
                },
                stateIcons = bottomBarStateIcons,
                backgroundColor = ColorAsset.G6,
                activateIconTint = Color.Unspecified
            ) { screenType ->
                selectedScreenType = screenType
            }
        }
    }
}
