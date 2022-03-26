/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeCoil.kt] created by Ji Sungbin on 22. 3. 26. 오후 11:20
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import team.applemango.runnerbe.shared.compose.default.RunnerbePlaceholderDefaults
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

@Composable
fun RunnerbeCoil(
    modifier: Modifier = Modifier,
    src: Any,
) {
    CoilImage(
        modifier = modifier,
        imageModel = src,
        shimmerParams = ShimmerParams(
            baseColor = RunnerbePlaceholderDefaults.BaseColor,
            highlightColor = RunnerbePlaceholderDefaults.HighlightColor,
        ),
        failure = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorAsset.G5_5)
            )
        }
    )
}
