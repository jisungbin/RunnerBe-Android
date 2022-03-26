/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FadingEdgeLazyColumn.kt] created by Ji Sungbin on 22. 3. 26. 오후 8:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.updateLayoutParams

enum class GradientLocate {
    Top,
    Bottom
}

@Composable
fun FadingEdgeLazyColumn(
    modifier: Modifier = Modifier,
    @ColorInt gradientTopColor: Int = Color.BLACK,
    gradientHeight: Dp = 30.dp,
    gradientLocate: Set<GradientLocate> = GradientLocate.values().toSet(),
    contentGap: Dp = 15.dp,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyListScope.() -> Unit,
) {
    Box(modifier = modifier) {
        if (gradientLocate.contains(GradientLocate.Top)) {
        }
        LazyColumn(
            modifier = Modifier.padding(top = contentGap),
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = content
        )
        if (gradientLocate.contains(GradientLocate.Bottom)) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gradientHeight),
                factory = { context ->
                    val gradientBackground = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        intArrayOf(gradientTopColor, Color.TRANSPARENT)
                    ).apply {
                        cornerRadius = 0f
                    }
                    View(context).apply {
                        updateLayoutParams {
                            height = LayoutParams.MATCH_PARENT
                            width = LayoutParams.MATCH_PARENT
                        }
                        background = gradientBackground
                    }
                }
            )
        }
    }
}

@Composable
private fun GradientView(
    height: Dp,
    @Size(value = 2) colors: IntArray,
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        factory = { context ->
            val gradientBackground = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            ).apply {
                cornerRadius = 0f
            }
            View(context).apply {
                updateLayoutParams {
                    this.height = LayoutParams.MATCH_PARENT
                    this.width = LayoutParams.MATCH_PARENT
                }
                background = gradientBackground
            }
        }
    )
}
