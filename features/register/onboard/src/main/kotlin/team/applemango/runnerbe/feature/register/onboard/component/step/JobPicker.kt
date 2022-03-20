/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [JobPicker.kt] created by Ji Sungbin on 22. 2. 10. 오전 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.shared.android.constant.DataStoreKey
import team.applemango.runnerbe.shared.android.extension.dataStore
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.compose.default.RunnerbeToggleButtonDefaults
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

// `vm: OnboardViewModel = activityViewModels()` 안한 이유:
// DFM 는 의존성이 반대로 되서 hilt 를 사용하지 못함
// 따라서 직접 factory 로 인자를 주입해 줘야 함
// 이는 OnboardActivity 에서 해주고 있으므로,
// OnboardActivity 에서 vm 를 가져와야 함
@Composable
internal fun JobPicker(
    vm: OnboardViewModel,
    jobSelectChanged: (isSelected: Boolean) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    var jobSelectState by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        val preferences = context
            .dataStore
            .data
            .defaultCatch(action = vm::emitException)
            .first()

        preferences[DataStoreKey.Onboard.Job]?.let { restoreJobCode ->
            jobSelectChanged(true)
            jobSelectState = Job.values().first { it.name == restoreJobCode }
        } ?: jobSelectChanged(false)
    }

    // FlowLayout 은 디자인이 안이쁘게 되서 직접 작성 함
    val jobLists = listOf(
        listOf(Job.PSV, Job.EDU, Job.DEV),
        listOf(Job.PSM, Job.DES),
        listOf(Job.MPR, Job.SER, Job.PRO),
        listOf(Job.RES, Job.SAF, Job.MED),
        listOf(Job.HUR, Job.ACC, Job.CUS)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = (35 - 16).dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = jobLists) { jobList ->
            LazyRow(horizontalArrangement = Arrangement.spacedBy(space = 12.dp)) {
                items(items = jobList) { job ->
                    ToggleButton(
                        colors = RunnerbeToggleButtonDefaults.colors(),
                        target = job,
                        selectState = jobSelectState,
                        targetStringBuilder = { job.string }
                    ) {
                        jobSelectState = job
                        jobSelectChanged(true)
                        coroutineScope.launch {
                            context.dataStore.edit { preferences ->
                                preferences[DataStoreKey.Onboard.Job] = job.name // job code
                            }
                        }
                    }
                }
            }
        }
    }
}
