/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FilterActivity.kt] created by Ji Sungbin on 22. 4. 5. 오후 4:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.filter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import team.applemango.runnerbe.feature.home.filter.component.FilterScreen
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.GradientAsset

class FilterActivity : ComponentActivity() {

    @OptIn(
        LocalActivityUsageApi::class, // LocalActivity
        ExperimentalFoundationApi::class // LocalOverScrollConfiguration
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()
        setWindowInsetsUsage()
        setContent {
            CompositionLocalProvider(
                LocalActivity provides this,
                LocalOverScrollConfiguration provides null
            ) {
                FilterScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.Background.Brush)
                        .statusBarsPadding()
                )
            }
        }
    }
}
