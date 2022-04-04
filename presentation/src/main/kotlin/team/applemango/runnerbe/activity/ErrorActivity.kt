package team.applemango.runnerbe.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jisungbin.erratum.ErratumExceptionActivity
import team.applemango.runnerbe.shared.android.constant.IntentKey
import team.applemango.runnerbe.shared.android.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

// 리소스로 분리하면 값들이 섞임 (shuffle)
class ErrorActivity : ErratumExceptionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra(IntentKey.ErrorType.Key) ?: IntentKey.ErrorType.Exception
        setWindowInsetsUsage()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = GradientAsset.Background.Brush),
                verticalArrangement = Arrangement.spacedBy(
                    space = 30.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = when (type) {
                        IntentKey.ErrorType.NoInternet -> "\uD83D\uDCAC" //  💬
                        else -> "\uD83D\uDEA7" // 🚧
                    },
                    style = LocalTextStyle.current.copy(fontSize = 100.sp)
                )
                Text(
                    text = when (type) {
                        IntentKey.ErrorType.NoInternet -> """
                            오프라인 상태에요!
                            네트워크를 연결해주세요.
                        """.trimIndent()
                        else -> """
                            코스를 이탈했어요!
                            다시 잘 찾아가볼까요?
                        """.trimIndent()
                    },
                    style = Typography.Header26B.copy(
                        color = ColorAsset.G1,
                        textAlign = TextAlign.Center
                    )
                )
                OutlinedButton(
                    shape = RoundedCornerShape(30.dp),
                    onClick = {
                        when (type) {
                            IntentKey.ErrorType.NoInternet -> {
                                changeActivityWithAnimation<StartActivity>()
                            }
                            else -> openLastActivity()
                        }
                    },
                    border = BorderStroke(width = 1.dp, color = ColorAsset.Primary),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                ) {
                    Text(
                        text = when (type) {
                            IntentKey.ErrorType.NoInternet -> "준비됐어요!"
                            else -> "출발할게요!"
                        },
                        style = Typography.Body14R.copy(color = ColorAsset.Primary)
                    )
                }
            }
        }
    }
}
