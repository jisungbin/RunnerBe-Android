/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [windowinsets.kt] created by Ji Sungbin on 22. 2. 10. 오후 1:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

@file:Suppress("NOTHING_TO_INLINE", "ComposableModifierFactory")

package team.applemango.runnerbe.shared.compose.util

import android.view.Window
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.systemuicontroller.SystemUiController

inline fun Modifier.systemBarsPaddingByDefaultColor(
    window: Window,
    defaultStatusBarColor: Color,
    defaultNavigationBarColor: Color,
    systemUiController: SystemUiController,
): Modifier = composed {
    val insets = LocalWindowInsets.current.systemBars
    val statusBarHeight: Dp
    val navigationBarHeight: Dp
    with(LocalDensity.current) {
        statusBarHeight = insets.top.toDp()
        navigationBarHeight = insets.bottom.toDp()
    }
    if (statusBarHeight.value * navigationBarHeight.value != 0f) { // insets 이 구해진 상태
        padding(top = statusBarHeight, bottom = navigationBarHeight)
    } else { // insets 이 0.dp 임 (구해지지 않음) -> window insets consume 비활성화, default color 세팅
        WindowCompat.setDecorFitsSystemWindows(window, true)
        systemUiController.setStatusBarColor(defaultStatusBarColor)
        systemUiController.setNavigationBarColor(defaultNavigationBarColor)
        this
    }
}
