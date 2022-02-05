/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLoginButton.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:42
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.feature.register.snslogin.R
import team.applemango.runnerbe.theme.Typography

private val KakaoContainerColor = Color(0xFFFEE500)
private val KakaoSymbolColor = Color(0xFF000000)
private val KakaoLabelColor = Color(0xD9000000) // #000000 85%

@Composable
internal fun KakaoLoginButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onClick() },
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = KakaoContainerColor)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                painter = painterResource(R.drawable.ic_kakao),
                contentDescription = null,
                tint = KakaoSymbolColor
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.component_button_kakao_login),
                style = Typography.Body16R.copy(
                    color = KakaoLabelColor,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewKakaoLoginButton() {
    KakaoLoginButton {}
}
