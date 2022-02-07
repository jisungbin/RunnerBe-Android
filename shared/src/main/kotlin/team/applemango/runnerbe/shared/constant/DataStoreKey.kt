/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DataKey.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.constant

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKey {
    object Login {
        val Uuid = stringPreferencesKey("login-uuid")
    }
}
