/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [EmployeeIdVerify.kt] created by Ji Sungbin on 22. 2. 12. 오전 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import team.applemango.runnerbe.shared.compose.extension.noRippleClickable
import team.applemango.runnerbe.shared.compose.extension.parseHtml
import team.applemango.runnerbe.shared.compose.extension.presentationDrawableOf
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.util.extension.toast

@Composable
internal fun EmployeeIdVerify(photo: Bitmap?, onPhotoChanged: (photo: Bitmap?) -> Unit) {
    val context = LocalContext.current
    var photoTakenTypeDialogVisible by remember { mutableStateOf(false) }
    val takePhotoFromAlbumIntent = remember {
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
    }

    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {
                onPhotoChanged(takenPhoto)
            } else {
                toast(context, StringAsset.Toast.ErrorTakenPhoto)
            }
        }

    // https://stackoverflow.com/questions/69360764/how-can-i-ask-for-multpile-mime-types-with-activityresultlauncher-getcontent
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    onPhotoChanged(uri.parseBitmap(context))
                } ?: run {
                    toast(context, StringAsset.Toast.ErrorTakenPhoto)
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
            photoTakenTypeDialogVisible = false
        },
        fromAlbumClick = {
            takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
            photoTakenTypeDialogVisible = false
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(color = ColorAsset.G6)
    ) {
        Crossfade(photo != null) { photoIsReady ->
            when (photoIsReady) {
                true -> {
                    PhotoScreen(photo = photo!!, onPhotoChanged = onPhotoChanged)
                }
                else -> {
                    PhotoPickScreen(photoPickFabClickAction = {
                        photoTakenTypeDialogVisible = true
                    })
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
                    text = StringAsset.Dialog.EmailVerifyTimeNotice,
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

@Composable
private fun PhotoScreen(photo: Bitmap, onPhotoChanged: (photo: Bitmap?) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(180.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        CoilImage(
            imageModel = photo,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier
                .noRippleClickable { onPhotoChanged(null) }
                .padding(16.dp),
            painter = rememberDrawablePainter(presentationDrawableOf("ic_round_close_24")),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = ColorAsset.G4_5)
        )
    }
}

@Composable
private fun PhotoPickScreen(photoPickFabClickAction: () -> Unit) {
    val noticeTexts = listOf(
        StringAsset.Hint.RequireFieldJob,
        StringAsset.Hint.RequireFieldInformation,
        StringAsset.Hint.RequireFieldProtect
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box( // 위에서 contentAlignment 로 그냥 하게 되면, FAB 이 Center Alignment 적용 되면서 순간이동함
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = { photoPickFabClickAction() },
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
        LazyColumn(
            modifier = Modifier.padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(noticeTexts) { notice ->
                Text(
                    text = notice.parseHtml(),
                    style = Typography.Body14R.copy(color = ColorAsset.G2_5)
                )
            }
        }
    }
}

@Suppress("DEPRECATION", "NewApi")
private fun Uri?.parseBitmap(context: Context): Bitmap? {
    return if (this != null) {
        val bitmap = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
            true -> {
                val source = ImageDecoder.createSource(context.contentResolver, this)
                ImageDecoder.decodeBitmap(source)
            }
            else -> {
                MediaStore.Images.Media.getBitmap(context.contentResolver, this)
            }
        }
        bitmap
    } else {
        toast(context, StringAsset.Toast.ErrorTakenPhoto)
        null
    }
}
