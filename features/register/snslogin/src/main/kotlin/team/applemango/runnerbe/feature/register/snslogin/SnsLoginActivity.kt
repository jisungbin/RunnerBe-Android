/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginActivity.kt] created by Ji Sungbin on 22. 2. 5. 오후 5:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import team.applemango.runnerbe.theme.Color
import team.applemango.runnerbe.theme.FontAsset

class SnsLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnsLoginScreen()
        }
    }

    @Composable
    private fun SnsLoginScreen() {
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            val (logo, buttons) = createRefs()

            Column(
                modifier = Modifier.constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(buttons.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "aaa",
                    style = TextStyle(
                        color = Color.Primary,
                        fontSize = 50.sp,
                        fontFamily = FontAsset.Aggro
                    )
                )
            }

            Column(
                modifier = Modifier.constrainAs(buttons) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom, 76.dp)
                },
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(3) {
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {}) { Text(it.toString()) }
                }
            }
        }
    }
}
