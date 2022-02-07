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
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.domain.login.constant.PlatformType
import team.applemango.runnerbe.feature.register.snslogin.di.ViewModelFactory
import team.applemango.runnerbe.feature.register.snslogin.di.component.DaggerViewModelComponent
import team.applemango.runnerbe.feature.register.snslogin.di.module.RepositoryModule
import team.applemango.runnerbe.feature.register.snslogin.di.module.UseCaseModule
import team.applemango.runnerbe.feature.register.snslogin.di.module.ViewModelModule
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginSideEffect
import team.applemango.runnerbe.feature.register.snslogin.mvi.LoginState
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.shared.util.extension.launchedWhenCreated
import team.applemango.runnerbe.shared.util.extension.toast
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.FontAsset
import javax.inject.Inject

private typealias string = team.applemango.runnerbe.shared.R.string
private typealias drawable = R.drawable

class SnsLoginActivity : ComponentActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var vm: SnsLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerViewModelComponent
            .builder()
            .repositoryModule(RepositoryModule(this)) // must activity context (startActivity calling)
            .useCaseModule(UseCaseModule())
            .viewModelModule(ViewModelModule())
            .build()
            .inject(this)

        vm = ViewModelProvider(this, viewModelFactory)[SnsLoginViewModel::class.java]
        vm.observe(lifecycleOwner = this, state = ::handleState, sideEffect = ::handleSideEffect)
        vm.exceptionFlow.collectWithLifecycle(this, ::handleException)

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
        val colorGradientEnd = Color(27, 26, 23)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(listOf(ColorAsset.G5_5, colorGradientEnd)))
                .systemBarsPadding(start = false, end = false)
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
                    modifier = Modifier.padding(top = 11.dp),
                    text = stringResource(string.app_name),
                    style = TextStyle(
                        color = ColorAsset.Primary,
                        fontSize = 50.sp,
                        fontFamily = FontAsset.Aggro
                    )
                )
            }

            Column(
                modifier = Modifier
                    .constrainAs(buttons) {
                        width = Dimension.matchParent
                        bottom.linkTo(parent.bottom, 76.dp)
                    },
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            vm.login(PlatformType.Kakao)
                        },
                    painter = painterResource(drawable.login_kakao),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            vm.login(PlatformType.Naver)
                        },
                    painter = painterResource(drawable.login_naver),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            vm.login(PlatformType.Apple)
                        },
                    painter = painterResource(drawable.login_apple),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

    private fun handleState(state: LoginState) {
        if (state.success) {
            toast(state.toString())
        }
    }

    private fun handleSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is LoginSideEffect.SaveUuid -> {
                launchedWhenCreated {
                    applicationContext.dataStore.edit { preference ->
                        preference[DataStoreKey.Login.Uuid] = sideEffect.uuid
                    }
                }
            }
        }
    }

    // TODO: handle exception in debug mode
    private fun handleException(exception: Throwable) {
        toast(exception.toString())
        logeukes(type = LoggerType.E) { exception }
    }
}
