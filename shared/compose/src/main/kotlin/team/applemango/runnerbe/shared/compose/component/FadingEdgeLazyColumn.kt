/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FadingEdgeLazyColumn.kt] created by Ji Sungbin on 22. 3. 26. 오후 8:23
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup.LayoutParams
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

// TODO: @Stable 남발?? 이게 맞나..
// 내가 이해한 바로는 이게 맞는데 ㅜㅜ
// 잘 쓰고 있는건지 모르겠다...

@Stable
object GradientDefaults {
    @Stable
    val Color = androidx.compose.ui.graphics.Color.Black

    @Stable
    val Height = 30.dp
}

@Stable
sealed class Gradient {
    @Immutable
    data class Top(
        val color: Color = GradientDefaults.Color,
        val height: Dp = GradientDefaults.Height,
    ) : Gradient()

    @Immutable
    data class Bottom(
        val color: Color = GradientDefaults.Color,
        val height: Dp = GradientDefaults.Height,
    ) : Gradient()
}

@Composable
fun FadingEdgeLazyColumn(
    modifier: Modifier = Modifier,
    gradients: Set<Gradient> = setOf(Gradient.Top(), Gradient.Bottom()),
    contentGap: Dp = 0.dp,
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
    val topGradient =
        remember(gradients) { gradients.find { it is Gradient.Top } as? Gradient.Top }
    val bottomGradient =
        remember(gradients) { gradients.find { it is Gradient.Bottom } as? Gradient.Bottom }

    ConstraintLayout(modifier = modifier) {
        val (topGradientRef, lazyColumnRef, bottomGradientRef) = createRefs()

        GradientView(
            modifier = Modifier
                .constrainAs(topGradientRef) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                    height = Dimension.value(topGradient?.height ?: GradientDefaults.Height)
                }
                .zIndex(2f),
            colors = intArrayOf(
                (topGradient?.color ?: GradientDefaults.Color).toArgb(),
                Color.Transparent.toArgb()
            ),
            visible = topGradient != null
        )
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumnRef) {
                    top.linkTo(
                        anchor = topGradientRef.top,
                        margin = when (topGradient != null) {
                            true -> contentGap
                            else -> 0.dp
                        }
                    )
                    bottom.linkTo(
                        anchor = bottomGradientRef.bottom,
                        margin = when (bottomGradient != null) {
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
                .constrainAs(bottomGradientRef) {
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.value(bottomGradient?.height ?: GradientDefaults.Height)
                }
                .zIndex(2f),
            colors = intArrayOf(
                Color.Transparent.toArgb(),
                (bottomGradient?.color ?: GradientDefaults.Color).toArgb(),
            ),
            visible = bottomGradient != null
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
