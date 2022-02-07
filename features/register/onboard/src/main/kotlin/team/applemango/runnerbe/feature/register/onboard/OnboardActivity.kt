/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardActivity.kt] created by Ji Sungbin on 22. 2. 8. 오전 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.Typography

class OnboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var stepIndex by remember { mutableStateOf(0) }
        }
    }

    @Composable
    private fun TopBar(
        stepIndex: Int,
        stepIndexUpdate: (step: Int) -> Unit,
        onBackAction: () -> Unit,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.clickable {
                    onBackAction()
                },
                painter = painterResource(R.drawable.ic_round_arrow_left_24),
                contentDescription = null
            )
            AnimatedVisibility(visible = stepIndex != 0) {
                Text(text = "$stepIndex/4", style = Typography.Body16R.copy(color = ColorAsset.G3))
            }
            Image(
                modifier = Modifier.clickable {
                    finish() // TODO: goto main activity
                },
                painter = painterResource(R.drawable.ic_round_close_24),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun BottomCTAButton() {
    }
}
