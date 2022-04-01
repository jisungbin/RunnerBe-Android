/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [color.kt] created by Ji Sungbin on 22. 2. 5. 오후 5:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.applemango.runnerbe.shared.compose.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush as ComposeBrush

object ColorAsset {
    val Primary = Color(0xFFFFE082)
    val PrimaryDark = Color(0xFFEED484)
    val PrimaryDarker = Color(0xFFC0AC75)

    val ErrorLight = Color(0xFFF59B92)
    val Error = Color(0xFFA70007)

    val G6 = Color(0xFF292620)
    val G5_5 = Color(0xFF36342D)
    val G5 = Color(0xFF413F39)
    val G4_5 = Color(0xFF595752)
    val G4 = Color(0xFF6F6D69)
    val G3_5 = Color(0xFF8D8C89)
    val G3 = Color(0xFFA8A8A5)
    val G2_5 = Color(0xFFBDBDBD)
    val G2 = Color(0xFFCDCDC9)
    val G1 = Color(0xFFF5F5F5)
}

object GradientAsset {
    object Background {
        val Top = Color(18, 18, 18)
        val TopHalf = Color(21, 21, 20)
        val Bottom = Color(27, 26, 23)
        val Brush = ComposeBrush.linearGradient(listOf(Top, Bottom))
    }

    object Fab {
        val Top = Color(85, 85, 85)
        val Bottom = Color(48, 48, 48)
        val Brush = ComposeBrush.linearGradient(listOf(Top, Bottom))
    }
}

@Composable
fun <T> animatedColorState(target: T, selectState: T, defaultColor: Color, selectedColor: Color) =
    animateColorAsState(
        if (target == selectState) selectedColor else defaultColor
    ).value
