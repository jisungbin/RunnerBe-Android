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
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.shared.domain.constant.FullDateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private typealias TimeTypeModel = TimeType

@Suppress("MemberVisibilityCanBePrivate")
internal class RunningDate(baseDate: Date = Date()) {
    private val calendar = Calendar.getInstance().apply {
        time = baseDate
    }

    companion object {
        sealed class Field {
            data class Date(val value: String) : Field()
            data class TimeType(val value: TimeTypeModel) : Field()
            data class Hour(val value: Int) : Field()
            data class Minute(val value: Int) : Field()
        }

        /**
         * 기본 값 불러오기
         *
         * 출근 전 - 06:00 AM, 퇴근 후 06:00 PM, 휴일 - 12:00 PM
         */
        fun getDefault(runningItemType: RunningItemType): RunningDate {
            val instance = RunningDate()
            with(instance) {
                when (runningItemType) {
                    RunningItemType.Before -> {
                        setTimeType(TimeType.AM)
                        setHour(6)
                    }
                    RunningItemType.After -> {
                        setTimeType(TimeType.PM)
                        setHour(6)
                    }
                    RunningItemType.Off -> {
                        setTimeType(TimeType.PM)
                        setHour(12)
                    }
                }
            }
            return instance
        }
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
     * AM/PM 설정
     *
     * @param timeType AM/PM 값 (캘린더 인자는 AM - 0, PM - 1 사용)
     */
    fun setTimeType(timeType: TimeTypeModel) {
        calendar.set(Calendar.AM_PM, TimeTypeModel.values().indexOf(timeType))
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
     * 설정된 달 조회
     */
    fun getMonth() = calendar.get(Calendar.MONTH)

    /**
     * 설정된 일 조회
     */
    fun getDay() = calendar.get(Calendar.DAY_OF_MONTH)

    /**
     * 설정된 시간 타입 조회
     */
    fun getTimeType() = TimeType.values()[calendar.get(Calendar.AM_PM)]

    /**
     * 설정된 시(hour) 조회
     */
    fun getHour() = calendar.get(Calendar.HOUR)

    /**
     * 설정된 분(minute) 조회
     */
    fun getMinute() = calendar.get(Calendar.MINUTE)

    /**
     * Date 객체 변환
     */
    fun toDate() = calendar.time ?: throw NullPointerException("Can't get time from calendar.")

    /**
     * Jetpack Compose 용 새로운 인스턴스 생성
     */
    fun newInstance() = RunningDate(toDate())

    override fun toString(): String {
        val dateString = SimpleDateFormat(FullDateFormat, Locale.KOREA).format(toDate())
        dateString ?: throw NullPointerException("Failed date formatting.")
        return dateString
    }

    operator fun compareTo(other: RunningDate) = toDate().compareTo(other.toDate())
}
