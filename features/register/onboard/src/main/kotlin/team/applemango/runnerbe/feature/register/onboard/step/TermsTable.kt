/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [TermsTable.kt] created by Ji Sungbin on 22. 2. 8. 오후 8:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.applemango.runnerbe.feature.register.asset.StringAsset
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.GradientAsset
import team.applemango.runnerbe.theme.Typography

private val VerticalPadding = 25.dp
private val HorizontalPadding = 12.dp
private val TermsTableShape = RoundedCornerShape(10.dp)

@Composable
internal fun TermsTable(onAllTermsChecked: () -> Unit) {
    var isAllTermsChecked by remember { mutableStateOf(false) }
    val termsCheckState = remember { mutableStateListOf(false, false, false) }
    val termsCheckboxColor = CheckboxDefaults.colors(
        checkedColor = ColorAsset.Primary,
        uncheckedColor = ColorAsset.G4,
        checkmarkColor = GradientAsset.EndColor
    )

    fun toggleAllTermsCheck() {
        if (isAllTermsChecked) {
            termsCheckState[0] = false
            termsCheckState[1] = false
            termsCheckState[2] = false
            isAllTermsChecked = false
        } else {
            termsCheckState[0] = true
            termsCheckState[1] = true
            termsCheckState[2] = true
            isAllTermsChecked = true
            onAllTermsChecked()
        }
    }

    fun checkAllChecked() {
        if (termsCheckState.all { it }) {
            isAllTermsChecked = true
            onAllTermsChecked()
        } else {
            isAllTermsChecked = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(TermsTableShape)
            .border(width = 1.dp, color = ColorAsset.G4, shape = TermsTableShape)
            .padding(vertical = VerticalPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAllTermsChecked,
                onCheckedChange = {
                    toggleAllTermsCheck()
                },
                colors = termsCheckboxColor
            )
            Text(
                modifier = Modifier.padding(start = HorizontalPadding),
                text = StringAsset.Content.CheckAllTerms,
                style = Typography.Body14R.copy(color = ColorAsset.G1)
            )
        }
        Spacer(
            modifier = Modifier
                .padding(vertical = VerticalPadding)
                .fillMaxWidth()
                .height(1.dp)
                .background(color = ColorAsset.G4)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(3) { number ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = HorizontalPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = termsCheckState[number],
                        onCheckedChange = {
                            termsCheckState[number] = !termsCheckState[number]
                            checkAllChecked()
                        },
                        colors = termsCheckboxColor
                    )
                    Text(
                        modifier = Modifier.padding(start = HorizontalPadding),
                        text = when (number) {
                            0 -> StringAsset.Content.CheckServiceTerm
                            1 -> StringAsset.Content.CheckPersonalInformationTerm
                            2 -> StringAsset.Content.CheckLocateTerm
                            else -> throw IndexOutOfBoundsException()
                        },
                        style = Typography.Body14R.copy(color = ColorAsset.G1)
                    )
                }
            }
        }
    }
}
