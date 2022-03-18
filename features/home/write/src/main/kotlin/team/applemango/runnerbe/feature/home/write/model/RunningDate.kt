/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningDate.kt] created by Ji Sungbin on 22. 3. 18. 오전 8:44
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.model

import androidx.annotation.IntRange
import java.util.Calendar
import java.util.Date
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.shared.domain.extension.format

private const val RunningDateFormat = "M/dd (E)"

internal fun Date.toDateString() = format(RunningDateFormat)

internal class RunningDate(baseDate: Date = Date()) {
    private val calendar = Calendar.getInstance().apply {
        time = baseDate
    }

    /**
     * AM/PM 설정
     *
     * @param timeType AM/PM 값 (캘린더 인자는 AM - 0, PM - 1 사용)
     */
    fun setTimeType(timeType: TimeType) {
        calendar.set(Calendar.AM_PM, TimeType.values().indexOf(timeType))
    }

    /**
     * 시간 설정
     *
     * @param hour 1-12 시간대 (캘린더 인자는 0-11 시간대 사용)
     */
    fun setHour(hour: Int) {
        calendar.set(Calendar.HOUR, hour - 1)
    }

    /**
     * 분 설정
     *
     * @param minute 분 (0 ~ 60)
     */
    fun setMinute(@IntRange(from = 0, to = 60) minute: Int) {
        calendar.set(Calendar.MINUTE, minute)
    }

    /**
     * 날짜 설정
     *
     * @param dateString 입력받는 포멧은 {월/일 (요일)} 이지만 월/일 만 사용됨
     * 캘린더의 월은 0 부터 시작이라 -1 을 해서 캘린더에 넣음
     */
    fun setDate(dateString: String) {
        val date = dateString.split(" ")[0]
        val (month, day) = date.split("/").map(String::toInt)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, day)
    }

    /**
     * Date 객체 반환
     *
     * @return Date
     */
    fun toDate() = calendar.time ?: throw NullPointerException("Can't get time from calendar.")

    operator fun compareTo(other: Date) = toDate().compareTo(other)
}
