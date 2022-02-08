/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [TermsTable.kt] created by Ji Sungbin on 22. 2. 8. 오후 8:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.rememberDrawablePainter
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.util.Web
import team.applemango.runnerbe.shared.util.presentationDrawableOf
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.GradientAsset
import team.applemango.runnerbe.theme.Typography

private val VerticalPadding = 25.dp
private val HorizontalPadding = 12.dp
private val TermsTableShape = RoundedCornerShape(10.dp)

@Composable
internal fun Activity.TermsTable(onAllTermsCheckStateChanged: (allChecked: Boolean) -> Unit) {
    val context = LocalContext.current
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
            onAllTermsCheckStateChanged(false)
        } else {
            termsCheckState[0] = true
            termsCheckState[1] = true
            termsCheckState[2] = true
            isAllTermsChecked = true
            onAllTermsCheckStateChanged(true)
        }
    }

    fun checkAllChecked() {
        if (termsCheckState.all { it }) {
            isAllTermsChecked = true
            onAllTermsCheckStateChanged(true)
        } else {
            isAllTermsChecked = false
            onAllTermsCheckStateChanged(false)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(3) { number ->
                val label: String
                val link: Web.Link
                when (number) {
                    0 -> {
                        label = StringAsset.Content.CheckServiceTerm
                        link = Web.Link.ServiceTerms
                    }
                    1 -> {
                        label = StringAsset.Content.CheckPersonalInformationTerm
                        link = Web.Link.PersonalInformationTerms
                    }
                    2 -> {
                        label = StringAsset.Content.CheckLocateTerm
                        link = Web.Link.LocateTerms
                    }
                    else -> throw IndexOutOfBoundsException()
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                            text = label,
                            style = Typography.Body14R.copy(color = ColorAsset.G1)
                        )
                    }
                    Image(
                        modifier = Modifier.clickable { Web.open(context, link) },
                        painter = rememberDrawablePainter(presentationDrawableOf("ic_round_arrow_right_24")),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(ColorAsset.G4)
                    )
                }
            }
        }
    }
}
