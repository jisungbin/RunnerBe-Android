/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SnsLoginScreen.kt] created by Ji Sungbin on 22. 2. 8. 오전 3:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.domain.register.runnerbe.constant.PlatformType
import team.applemango.runnerbe.feature.register.snslogin.SnsLoginViewModel
import team.applemango.runnerbe.shared.compose.extension.presentationDrawableOf
import team.applemango.runnerbe.shared.compose.extension.presentationStringOf
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontAsset

@Composable
internal fun SnsLoginScreen(
    modifier: Modifier = Modifier,
    vm: SnsLoginViewModel
) {
    ConstraintLayout(modifier = modifier) {
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
                painter = rememberDrawablePainter(presentationDrawableOf("ic_logo_symbol")),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 11.dp),
                text = presentationStringOf("app_name"),
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
                painter = rememberDrawablePainter(presentationDrawableOf("login_kakao")),
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
                painter = rememberDrawablePainter(presentationDrawableOf("login_naver")),
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
                painter = rememberDrawablePainter(presentationDrawableOf("login_apple")),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
