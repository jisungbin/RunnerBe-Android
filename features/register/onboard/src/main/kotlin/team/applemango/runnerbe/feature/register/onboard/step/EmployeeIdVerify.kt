/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [EmployeeIdVerify.kt] created by Ji Sungbin on 22. 2. 12. 오전 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import android.graphics.Bitmap
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.util.presentationDrawableOf

@Composable
internal fun EmployeeIdVerify() {
    var photo by remember { mutableStateOf<Bitmap?>(null) }
    var photoTakenTypeDialogVisible by remember { mutableStateOf(false) }

    PhotoTakenTypeDialog(visible = photoTakenTypeDialogVisible, onDismissRequest = {
        photoTakenTypeDialogVisible = false
    })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = ColorAsset.G6),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(photo != null) { photoIsReady ->
            when (photoIsReady) {
                true -> {
                    CoilImage(
                        imageModel = photo,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                else -> {
                    FloatingActionButton(
                        onClick = {
                            photoTakenTypeDialogVisible = true
                        },
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 3.dp,
                            pressedElevation = 6.dp,
                            hoveredElevation = 6.dp,
                            focusedElevation = 6.dp
                        ),
                        backgroundColor = ColorAsset.G5
                    ) {
                        Image(
                            painter = rememberDrawablePainter(presentationDrawableOf("ic_round_add_24")),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = ColorAsset.G2)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoTakenTypeDialog(visible: Boolean, onDismissRequest: () -> Unit) {
    if (visible) {
        AlertDialog(
            buttons = {},
            shape = RoundedCornerShape(12.dp),
            backgroundColor = ColorAsset.G5,
            onDismissRequest = { onDismissRequest() },
            text = {
                Column(modifier = Modifier.wrapContentSize()) {
                    Text(
                        modifier = Modifier.padding(24.dp),
                        text = StringAsset.Dialog.TitleVerifyNotice,
                        style = Typography.Body14R.copy(color = ColorAsset.G1)
                    )
                    Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G4_5)
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                        text = StringAsset.Dialog.FromCamera,
                        style = Typography.Body16R.copy(color = ColorAsset.Primary)
                    )
                    Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G4_5)
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                        text = StringAsset.Dialog.FromAlbum,
                        style = Typography.Body16R.copy(color = ColorAsset.Primary)
                    )
                }
            }
        )
    }
}
