/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [typography.kt] created by Ji Sungbin on 22. 2. 5. 오전 12:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

@file:Suppress("unused")

package team.applemango.runnerbe.shared.compose.theme

import android.content.Context
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import team.applemango.runnerbe.shared.compose.R

private typealias font = R.font

private fun Int.toFontFamily() = FontFamily(Font(this))

// 굵기: R < M < B
object FontAsset {
    val Aggro = font.aggro_l.toFontFamily()

    object NotoSans {
        val R = font.notosans_r.toFontFamily()
        val M = font.notosans_m.toFontFamily()
        val B = font.notosans_b.toFontFamily()
    }

    object Roboto {
        val R = font.roboto_r.toFontFamily()
        val M = font.roboto_m.toFontFamily()
    }
}

object FontTypeface {
    object Roboto {
        fun medium(context: Context) = ResourcesCompat.getFont(context, font.roboto_m)
        fun regular(context: Context) = ResourcesCompat.getFont(context, font.roboto_r)
    }
}

object Typography {
    val Header28M = TextStyle(
        fontFamily = FontAsset.NotoSans.M,
        fontSize = 28.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.84).sp
    )
    val Header28B = TextStyle(
        fontFamily = FontAsset.NotoSans.B,
        fontSize = 28.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.84).sp
    )
    val Title20M = TextStyle(
        fontFamily = FontAsset.NotoSans.M,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.12).sp
    )
    val Title20R = TextStyle(
        fontFamily = FontAsset.NotoSans.R,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.12).sp
    )
    val Title18R = TextStyle(
        fontFamily = FontAsset.NotoSans.R,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        letterSpacing = (-0.11).sp
    )
    val Title16M = TextStyle(
        fontFamily = FontAsset.NotoSans.M,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.4).sp
    )
    val Body16R = TextStyle(
        fontFamily = FontAsset.NotoSans.R,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.4).sp
    )
    val EngBody16R = TextStyle(
        fontFamily = FontAsset.Roboto.R,
        fontSize = 16.sp,
        lineHeight = 27.sp
    )
    val Body14B = TextStyle(
        fontFamily = FontAsset.NotoSans.B,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.6).sp
    )
    val Body14M = TextStyle(
        fontFamily = FontAsset.NotoSans.M,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.6).sp
    )
    val Body14R = TextStyle(
        fontFamily = FontAsset.NotoSans.R,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.4).sp
    )
    val EngBody14R = TextStyle(
        fontFamily = FontAsset.Roboto.R,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )
    val Body12B = TextStyle(
        fontFamily = FontAsset.NotoSans.B,
        fontSize = 12.sp,
        letterSpacing = (-0.2).sp
    )
    val Body12M = TextStyle(
        fontFamily = FontAsset.NotoSans.M,
        fontSize = 12.sp,
        letterSpacing = (-0.2).sp
    )
    val EngBody12M = TextStyle(
        fontFamily = FontAsset.Roboto.M,
        fontSize = 12.sp,
        lineHeight = 15.sp
    )
    val Body12R = TextStyle(
        fontFamily = FontAsset.NotoSans.R,
        fontSize = 12.sp,
        letterSpacing = (-0.3).sp
    )
    val Caption10B = TextStyle(
        fontFamily = FontAsset.NotoSans.B,
        fontSize = 10.sp
    )
    val Caption10R = TextStyle(
        fontFamily = FontAsset.NotoSans.R,
        fontSize = 10.sp,
        letterSpacing = (-0.2).sp
    )

    object Custom {
        val MainBoardTitle = TextStyle(
            fontFamily = FontAsset.Aggro,
            color = ColorAsset.PrimaryDark,
            fontSize = 16.sp,
            lineHeight = 31.sp,
            letterSpacing = (-0.4).sp
        )
        val SuperWheelPickerBold = TextStyle(
            fontFamily = FontAsset.Roboto.M,
            color = ColorAsset.Primary,
            fontSize = 26.sp,
            letterSpacing = (-0.26).sp
        )
        val SuperWheelPickerRegular = TextStyle(
            fontFamily = FontAsset.Roboto.R,
            color = ColorAsset.Primary,
            fontSize = 18.sp,
            lineHeight = 27.sp,
            letterSpacing = (-0.11).sp
        )
        val MapMarker = TextStyle(
            fontFamily = FontAsset.Roboto.R,
            color = ColorAsset.G1,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = (-0.12).sp
        )
        val WriteRunningItemType = TextStyle(
            fontFamily = FontAsset.NotoSans.M,
            color = ColorAsset.PrimaryDarker,
            fontSize = 9.sp,
            letterSpacing = (-0.39).sp
        )
        val RunningItemDetailTitle = TextStyle(
            fontFamily = FontAsset.NotoSans.R,
            color = ColorAsset.G1,
            fontSize = 22.sp,
            letterSpacing = (-0.22).sp
        )
    }
}
