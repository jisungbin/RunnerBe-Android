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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

enum class GradientLocate {
    Top,
    Bottom
}

@Composable
fun FadingEdgeLazyColumn(
    modifier: Modifier = Modifier,
    @ColorInt gradientColor: Int = Color.BLACK,
    gradientHeight: Dp = 30.dp,
    gradientLocate: Set<GradientLocate> = GradientLocate.values().toSet(),
    contentGap: Dp = gradientHeight / 2,
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
    val topGradient = remember { gradientLocate.contains(GradientLocate.Top) }
    val bottomGradient = remember { gradientLocate.contains(GradientLocate.Bottom) }

    ConstraintLayout(modifier = modifier) {
        val (top, lazyColumn, bottom) = createRefs()

        GradientView(
            modifier = Modifier
                .constrainAs(top) {
                    this.top.linkTo(parent.top)
                    width = Dimension.matchParent
                    height = Dimension.value(gradientHeight)
                }
                .zIndex(2f),
            colors = intArrayOf(gradientColor, Color.TRANSPARENT),
            visible = topGradient
        )
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumn) {
                    this.top.linkTo(
                        anchor = top.top,
                        margin = when (topGradient) {
                            true -> contentGap
                            else -> 0.dp
                        }
                    )
                    this.bottom.linkTo(
                        anchor = bottom.bottom,
                        margin = when (bottomGradient) {
                            true -> contentGap
                            else -> 0.dp
                        }
                    )
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
                .zIndex(1f),
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = content
        )
        GradientView(
            modifier = Modifier
                .constrainAs(bottom) {
                    this.bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.value(gradientHeight)
                }
                .zIndex(2f),
            colors = intArrayOf(Color.TRANSPARENT, gradientColor),
            visible = bottomGradient
        )
    }
}

@Composable
private fun GradientView(
    modifier: Modifier = Modifier,
    @Size(value = 2) colors: IntArray,
    visible: Boolean = true,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val gradientBackground = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            ).apply {
                cornerRadius = 0f
            }
            View(context).apply {
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
                background = gradientBackground
                visibility = when (visible) {
                    true -> View.VISIBLE
                    else -> View.INVISIBLE
                }
            }
        }
    )
}
