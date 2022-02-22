/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [modifier.kt] created by Ji Sungbin on 22. 2. 12. 오전 3:25
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

@file:Suppress("NOTHING_TO_INLINE")

package team.applemango.runnerbe.shared.compose.extension

import android.view.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.systemuicontroller.SystemUiController

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() },
    )
}

// Feature: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/13
// Original function implementation is inlined
// Not working -> internal function
internal inline fun Modifier.systemBarsPaddingByDefaultColor(
    window: Window,
    defaultStatusBarColor: Color,
    defaultNavigationBarColor: Color,
    systemUiController: SystemUiController,
) = composed {
    val insets = LocalWindowInsets.current.systemBars
    val statusBarHeight: Dp
    val navigationBarHeight: Dp
    with(LocalDensity.current) {
        statusBarHeight = insets.top.toDp() // dp 로 바꾸는 함수도 스코프에 따라 값이 달라짐 (Density 스코프에서 돌려야 함)
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
