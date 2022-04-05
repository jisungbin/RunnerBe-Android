package team.applemango.runnerbe.feature.home.filter

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FilterActivity.kt] created by Ji Sungbin on 22. 4. 5. 오후 4:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import team.applemango.runnerbe.feature.home.filter.component.FilterScreen
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.compose.extension.LocalActivity
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi
import team.applemango.runnerbe.shared.compose.theme.GradientAsset

class FilterActivity : ComponentActivity() {

    @OptIn(LocalActivityUsageApi::class) // LocalActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowInsetsUsage()
        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                FilterScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.Background.Brush)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}
