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
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.shared.compose.component.CustomAlertDialog
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.util.noRippleClickable
import team.applemango.runnerbe.shared.compose.util.presentationDrawableOf
import team.applemango.runnerbe.shared.util.extension.toast

@Composable
internal fun EmployeeIdVerify() {
    var photo by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    var photoTakenTypeDialogVisible by remember { mutableStateOf(false) }

    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            photo = bitmap
        }

    @Suppress("DEPRECATION")
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                photo = when (Build.VERSION.SDK_INT >= 28) {
                    true -> {
                        val source = ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                    else -> {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    }
                }
            } else {
                toast(context, StringAsset.Toast.ErrorTakenPhoto)
            }
        }

    PhotoTakenTypeDialog(
        visible = photoTakenTypeDialogVisible,
        onDismissRequest = {
            photoTakenTypeDialogVisible = false
        },
        fromCameraClick = {
            takePhotoFromCameraLauncher.launch()
        },
        fromAlbumClick = {
            takePhotoFromAlbumLauncher.launch("image/*")
        }
    )

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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                        CoilImage(
                            imageModel = photo,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                        Image(
                            modifier = Modifier
                                .noRippleClickable { photo = null }
                                .padding(16.dp),
                            painter = rememberDrawablePainter(presentationDrawableOf("ic_round_close_24")),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = ColorAsset.G4_5)
                        )
                    }
                }
                else -> {
                    FloatingActionButton(
                        onClick = { photoTakenTypeDialogVisible = true },
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
private fun PhotoTakenTypeDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    fromCameraClick: () -> Unit,
    fromAlbumClick: () -> Unit,
) {
    if (visible) {
        CustomAlertDialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = ColorAsset.G5)
            ) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = StringAsset.Dialog.TitleVerifyNotice,
                    style = Typography.Body14R.copy(color = ColorAsset.G1)
                )
                Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G4_5)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable { fromCameraClick() }
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    text = StringAsset.Dialog.FromCamera,
                    style = Typography.Body16R.copy(color = ColorAsset.Primary)
                )
                Divider(modifier = Modifier.fillMaxWidth(), color = ColorAsset.G4_5)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable { fromAlbumClick() }
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp, bottom = 20.dp),
                    text = StringAsset.Dialog.FromAlbum,
                    style = Typography.Body16R.copy(color = ColorAsset.Primary)
                )
            }
        }
    }
}
