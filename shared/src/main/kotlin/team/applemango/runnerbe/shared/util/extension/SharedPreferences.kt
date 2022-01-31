/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SharedPreferences.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util.extension

import android.content.SharedPreferences
import androidx.core.content.edit

operator fun SharedPreferences.get(name: String, default: String? = null) = getString(name, default)

operator fun SharedPreferences.set(name: String, value: String) = edit { putString(name, value) }
