/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DateCache.kt] created by Ji Sungbin on 22. 3. 18. 오전 9:37
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.util

import java.util.Calendar
import java.util.Date
import team.applemango.runnerbe.shared.domain.extension.toCalendar

internal object DateCache {
    private val cachedPlusDate = HashMap<Int, Date>()

    fun getCachedIndexFromDay(day: Int, default: Int = 0): Int {
        val index = cachedPlusDate.values.indexOfFirst { date ->
            date.toCalendar().get(Calendar.DAY_OF_MONTH) == day
        }
        return if (index != -1) index else default
    }

    fun Date.plusDayAndCaching(plus: Int = 1): Date {
        if (cachedPlusDate[plus] == null) {
            val calendar = toCalendar().apply {
                add(Calendar.DAY_OF_MONTH, plus)
            }
            cachedPlusDate[plus] = calendar.time
        }
        return cachedPlusDate[plus]!!
    }
}
