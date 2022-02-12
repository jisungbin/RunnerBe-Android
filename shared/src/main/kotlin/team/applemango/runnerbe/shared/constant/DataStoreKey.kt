/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DataKey.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.constant

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKey {
    object Login {
        val Jwt = stringPreferencesKey("login-jwt")
        val Uuid = stringPreferencesKey("login-uuid")
    }

    object Onboard {
        val TermsAllCheck = booleanPreferencesKey("onboard-terms_all_check") // 동의 여부 boolean
        val Year = intPreferencesKey("onboard-birthday") // 입력한 년도
        val Gender = stringPreferencesKey("onboard-gender") // Gender enum class string value
        val Job = stringPreferencesKey("onboard-job") // Job enum class name
        val Email = stringPreferencesKey("onboard-email") // 입력한 이메일
        val VerifyWithEmployeeId = booleanPreferencesKey("onboard-verify_with_employee_id")
        val VerifyWithEmailDone = booleanPreferencesKey("onboard-verify_with_email_done")
        val VerifyWithEmployeeIdRequestDone =
            booleanPreferencesKey("onboard-verify_with_employee_id_done")
    }
}
