/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [typography.kt] created by Ji Sungbin on 22. 2. 5. 오전 12:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

@file:Suppress("unused")

package team.applemango.runnerbe.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

private typealias font = R.font

private fun Int.toFontFamily() = FontFamily(Font(this))

object FontAsset {
    val Aggro = font.aggro_l.toFontFamily()

    object NotoSans {
        val M = font.notosans_m.toFontFamily()
        val R = font.notosans_r.toFontFamily()
        val B = font.notosans_b.toFontFamily()
    }

    object Roboto {
        val R = font.roboto_r.toFontFamily()
        val M = font.roboto_m.toFontFamily()
    }
}

object Typography {
    val Header28M = TextStyle(
        fontFamily = FontAsset.NotoSans.M,
        fontSize = 28.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.84).sp
    )
    val Title16B = TextStyle(
        fontFamily = FontAsset.NotoSans.B,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.4).sp
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
}
