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
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import team.applemango.runnerbe.feature.register.snslogin.module.A
import team.applemango.runnerbe.feature.register.snslogin.module.DaggerRepositoryComponent
import team.applemango.runnerbe.feature.register.snslogin.module.RepositoryModule
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.FontAsset
import javax.inject.Inject
import team.applemango.runnerbe.shared.util.extension.toast

private typealias string = team.applemango.runnerbe.shared.R.string
private typealias drawable = R.drawable

class SnsLoginActivity : ComponentActivity() {

    // private val vm: SnsLoginViewModel by viewModels()

    @Inject
    lateinit var a: A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerRepositoryComponent.builder().repositoryModule(RepositoryModule()).build()
            .inject(this)
        toast(a.b)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                SnsLoginScreen()
            }
        }
    }

    @Preview
    @Composable
    private fun SnsLoginScreen() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(listOf(ColorAsset.G5_5, ColorAsset.G6)))
                .systemBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            val (logo, buttons) = createRefs()

            Column(
                modifier = Modifier.constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(buttons.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(drawable.ic_logo_symbol),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = stringResource(string.app_name),
                    style = TextStyle(
                        color = ColorAsset.Primary,
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
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    painter = painterResource(drawable.kakao_login),
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    painter = painterResource(drawable.naver_login),
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    painter = painterResource(drawable.apple_login),
                    contentDescription = null
                )
            }
        }
    }
}