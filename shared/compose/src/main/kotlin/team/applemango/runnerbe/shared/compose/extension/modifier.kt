/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [modifier.kt] created by Ji Sungbin on 22. 2. 12. 오전 3:25
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() },
    )
}

fun Modifier.verticalInsetsPadding() = composed {
    windowInsetsPadding(
        WindowInsets.systemBars.only(
            WindowInsetsSides.Vertical
        )
    )
}

fun Modifier.defaultPlaceholder(visible: Boolean) = then(
    placeholder(
        visible = visible,
        color = ColorAsset.G5_5,
        highlight = PlaceholderHighlight.fade(
            highlightColor = ColorAsset.G5_5.copy(alpha = .7f)
        )
    )
)
