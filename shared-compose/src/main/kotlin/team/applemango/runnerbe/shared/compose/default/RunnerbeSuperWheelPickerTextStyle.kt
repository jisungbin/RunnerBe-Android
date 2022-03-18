/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeSuperWheelPickerTextStyle.kt] created by Ji Sungbin on 22. 3. 18. 오후 9:47
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.default

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import team.applemango.runnerbe.shared.compose.theme.FontTypeface
import team.applemango.runnerbe.shared.domain.unit.em
import team.applemango.runnerbe.shared.domain.unit.px
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPickerTextStyle

val RunnerbeSuperWheelPickerTextStyle
    @Composable get() = run {
        val context = LocalContext.current
        SuperWheelPickerTextStyle(
            typeface = FontTypeface.Roboto.regular(context),
            textSize = 24.px,
            letterSpacing = (-0.24).em
        )
    }
