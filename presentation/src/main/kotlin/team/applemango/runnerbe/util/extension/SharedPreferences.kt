package team.applemango.runnerbe.util.extension

import android.content.SharedPreferences
import androidx.core.content.edit

operator fun SharedPreferences.get(name: String, default: String? = null) = getString(name, default)

operator fun SharedPreferences.set(name: String, value: String) = edit { putString(name, value) }
