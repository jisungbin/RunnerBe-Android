/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [googlemap.kt] created by Ji Sungbin on 22. 3. 19. 오전 3:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import java.util.Locale

fun Locate.toLatLng() = LatLng(latitude, longitude)

fun LatLng.toAddress(context: Context): String {
    val geoCoder = Geocoder(context, Locale.KOREA)
    val addressList = geoCoder.getFromLocation(latitude, longitude, 2)
    return addressList[1].getAddressLine(0).replace("대한민국 ", "")
}

// https://stackoverflow.com/a/45564994/14299073
fun Context.bitmapDescriptorFromVector(
    @DrawableRes vectorResId: Int
): BitmapDescriptor? {
    return ContextCompat.getDrawable(this, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
