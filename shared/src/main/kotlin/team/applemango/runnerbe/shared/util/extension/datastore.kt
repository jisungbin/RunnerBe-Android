/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [datastore.kt] created by Ji Sungbin on 22. 2. 6. 오전 2:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util.extension

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val RunnerbePreferenceDataStore = "runnerbe_preference_datastore"

val Context.dataStore by preferencesDataStore(
    name = RunnerbePreferenceDataStore
)
