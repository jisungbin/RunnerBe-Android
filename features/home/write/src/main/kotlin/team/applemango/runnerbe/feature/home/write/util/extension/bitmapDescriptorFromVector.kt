package team.applemango.runnerbe.feature.home.write.util.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

// https://stackoverflow.com/a/45564994/14299073
internal fun Context.bitmapDescriptorFromVector(
    @DrawableRes vectorResId: Int
): BitmapDescriptor? {
    return ContextCompat.getDrawable(this, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
