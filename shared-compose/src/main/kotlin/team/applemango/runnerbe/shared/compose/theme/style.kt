/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [style.kt] created by Ji Sungbin on 22. 3. 2. 오후 3:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.theme

import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable

val RunnerbeCheckBoxColors
    @Composable
    get() = CheckboxDefaults.colors(
        checkedColor = ColorAsset.Primary, // 테두리, 내부 색상
        uncheckedColor = ColorAsset.G4, // 테두리 색상, 내부는 투명 처리
        checkmarkColor = GradientAsset.BlackEndColor // checked 상태일때 체크 아이콘 색상
    )
