/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [JobPicker.kt] created by Ji Sungbin on 22. 2. 10. 오전 5:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.shared.compose.component.ToggleButton
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.dataStore

@Composable
internal fun JobPicker(jobSelectChanged: (isSelected: Boolean) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var jobSelectState by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        context.dataStore.data.cancellable().collect { preferences ->
            preferences[DataStoreKey.Onboard.Job]?.let { restoreJobCode ->
                jobSelectChanged(true)
                jobSelectState = Job.values().first { it.name == restoreJobCode }
            } ?: jobSelectChanged(false)
            cancel("onboard restore execute must be once.")
        }
    }

    // FlowLayout 은 디자인이 안이쁘게 되서 수동으로 작성 함
    /*FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = (35 - 16).dp),
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        mainAxisSpacing = 12.dp,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        crossAxisSpacing = 6.dp,
    ) {
        Job.values().forEach { job ->
            ToggleButton(
                target = job,
                selectState = jobSelectState,
                targetStringBuilder = { job.string }
            ) {
                jobSelectState = job
                jobSelectChanged(true)
                coroutineScope.launch {
                    context.dataStore.edit { preferences ->
                        preferences[DataStoreKey.Onboard.Job] = job.name // code
                    }
                }
            }
        }
    }*/

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
        items(jobLists) { jobList ->
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(jobList) { job ->
                    ToggleButton(
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
