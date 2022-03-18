/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [date.kt] created by Ji Sungbin on 22. 3. 2. 오후 10:03
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Suppress("HasPlatformType")
fun Date.format(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(this)

@Suppress("HasPlatformType")
fun Date.toCalendar() = Calendar.getInstance().apply { time = this@toCalendar }
