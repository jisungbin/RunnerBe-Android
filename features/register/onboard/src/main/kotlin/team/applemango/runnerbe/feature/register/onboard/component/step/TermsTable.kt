/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [TermsTable.kt] created by Ji Sungbin on 22. 2. 8. 오후 8:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component.step

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.skydoves.landscapist.rememberDrawablePainter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.util.Web
import team.applemango.runnerbe.shared.compose.extension.presentationDrawableOf
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.RunnerbeCheckBoxColors
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.domain.util.flowExceptionMessage
import team.applemango.runnerbe.shared.android.extension.dataStore

private val VerticalPadding = 25.dp
private val HorizontalPadding = 12.dp
private val TermsTableShape = RoundedCornerShape(10.dp)

@Composable
internal fun TermsTable(
    vm: OnboardViewModel,
    onAllTermsCheckStateChanged: (allChecked: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isAllTermsCheckedState by remember { mutableStateOf(false) }
    val termsCheckState = remember { mutableStateListOf(false, false, false) }

    fun saveTermsAllCheckState() {
        coroutineScope.launch {
            context.dataStore.edit { preferences ->
                preferences[DataStoreKey.Onboard.TermsAllCheck] = isAllTermsCheckedState
            }
        }
    }

    fun toggleAllTermsCheck(nowState: Boolean = isAllTermsCheckedState) { // 전체 동의 버튼 토글
        isAllTermsCheckedState = if (nowState) { // true -> false
            repeat(3) { index ->
                termsCheckState[index] = false
            }
            false
        } else { // false -> true
            repeat(3) { index ->
                termsCheckState[index] = true
            }
            true
        }
        onAllTermsCheckStateChanged(isAllTermsCheckedState)
        saveTermsAllCheckState()
    }

    fun checkAllChecked() { // 개별 동의 버튼 토글 후, 전체 동의 됐는지 체크
        isAllTermsCheckedState = termsCheckState.all { it }
        onAllTermsCheckStateChanged(isAllTermsCheckedState)
        saveTermsAllCheckState()
    }

    // 위치 고정 (toggleAllTermsCheck 함수 사용)
    LaunchedEffect(Unit) {
        context
            .dataStore
            .data
            .cancellable()
            .catch { exception ->
                vm.emitException(
                    Exception(
                        flowExceptionMessage(
                            flowName = "TermsTable DataStore",
                            exception = exception
                        )
                    )
                )
            }
            .collect { preferences ->
                if (preferences[DataStoreKey.Onboard.TermsAllCheck] == true) {
                    toggleAllTermsCheck(nowState = false) // state toggle -> false -> true
                } else {
                    onAllTermsCheckStateChanged(false)
                }
                cancel("onboard restore execute must be once.")
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
                checked = isAllTermsCheckedState,
                onCheckedChange = {
                    toggleAllTermsCheck()
                },
                colors = RunnerbeCheckBoxColors
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
                            colors = RunnerbeCheckBoxColors
                        )
                        Text(
                            modifier = Modifier.padding(start = HorizontalPadding),
                            text = label,
                            style = Typography.Body14R.copy(color = ColorAsset.G1)
                        )
                    }
                    Icon(
                        modifier = Modifier.clickable { Web.open(context, link) },
                        painter = rememberDrawablePainter(presentationDrawableOf("ic_round_arrow_right_24")),
                        contentDescription = null,
                        tint = ColorAsset.G4
                    )
                }
            }
        }
    }
}
