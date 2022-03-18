/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningDate.kt] created by Ji Sungbin on 22. 3. 18. 오전 8:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.shared.domain.extension.format

private const val RunningDateFormat = "M/dd (E)"

internal fun Date.toDateString() = format(RunningDateFormat)

internal data class RunningDate(
    val dateString: String,
    val timeType: TimeType,
    val hour: Int,
    val minute: Int,
) {
    private companion object {
        const val FullDateFormat = "$RunningDateFormat a h m"
    }

    override fun toString() = "$dateString ${timeType.string} $hour $minute"

    private fun toDate(): Date {
        val date = SimpleDateFormat(
            FullDateFormat,
            Locale.getDefault()
        ).parse(toString())
        date ?: throw IllegalStateException("${toString()} is invalid format.")
        return date
    }

    operator fun compareTo(other: Date): Int {
        return other.compareTo(toDate())
    }
}
