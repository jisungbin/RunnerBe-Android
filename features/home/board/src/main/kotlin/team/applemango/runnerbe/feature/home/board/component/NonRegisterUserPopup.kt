package team.applemango.runnerbe.feature.home.board.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.shared.compose.extension.parseHtml
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

@Composable
internal fun NonRegisterUserPopup(
    modifier: Modifier = Modifier,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.mainboard_unregisteruser_popup_title),
            style = Typography.Header28B.copy(
                color = ColorAsset.Primary,
                textAlign = TextAlign.Center
            ),
        )
        Image(
            modifier = Modifier
                .padding(vertical = 30.dp)
                .size(200.dp),
            painter = painterResource(R.drawable.ic_logo_symbol),
            contentDescription = null
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onPositiveButtonClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = ColorAsset.Primary),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.mainboard_unregisteruser_popup_button_let_me_introduce),
                style = Typography.Body14M.copy(color = Color.Black)
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable(onClick = onNegativeButtonClick),
            text = stringResource(R.string.mainboard_unregisteruser_popup_button_no_now).parseHtml(),
            style = Typography.Body12M.copy(color = ColorAsset.G3)
        )
    }
}
