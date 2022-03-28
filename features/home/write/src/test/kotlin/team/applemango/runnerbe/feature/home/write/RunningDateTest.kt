/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ExampleUnitTest.kt] created by Ji Sungbin on 22. 1. 31. 오후 9:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write

import java.util.Date
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.util.DateCache.plusDayAndCaching

class RunningDateTest {
    @Test
    @DisplayName("내일 < 어제 가 성립해야 함")
    fun compareToRunningDateTrue() {
        val today = RunningDate()
        val tomorrow = RunningDate(Date().plusDayAndCaching())
        assertTrue(today < tomorrow)
    }

    @Test
    @DisplayName("어제 > 오늘 이 틀려야 함")
    fun compareToRunningDateFalse() {
        val today = RunningDate()
        val tomorrow = RunningDate(Date().plusDayAndCaching())
        assertFalse(today > tomorrow)
    }

    @Test
    @DisplayName("같은 오늘 date 로 hour 만 다를 때 비교가 성공해야 함 - big > small")
    fun compareToSameDateInstanceAndDifferentHourTrue() {
        val today = RunningDate().apply {
            setHour(10)
        }
        val today2 = RunningDate().apply {
            setHour(9)
        }
        assertTrue(today > today2)
    }

    @Test
    @DisplayName("같은 오늘 date 로 hour 만 다를 때 비교가 실패해야 함 - big > small")
    fun compareToSameDateInstanceAndDifferentHourFalse() {
        val today = RunningDate().apply {
            setHour(10)
        }
        val today2 = RunningDate().apply {
            setHour(9)
        }
        assertFalse(today2 > today)
    }

    @Test
    @DisplayName("같은 오늘 date 로 TimeType 만 다를 때 비교가 성공해야 함 - big > small")
    fun compareToSameDateInstanceAndDifferentHourTimeTypeTrue() {
        val today = RunningDate().apply {
            setTimeType(TimeType.AM)
        }
        val today2 = RunningDate().apply {
            setTimeType(TimeType.PM)
        }
        assertTrue(today < today2)
    }

    @Test
    @DisplayName("같은 오늘 date 로 TimeType 만 다를 때 비교가 실패해야 함 - big > small")
    fun compareToSameDateInstanceAndDifferentHourTimeTypeFalse() {
        val today = RunningDate().apply {
            setTimeType(TimeType.AM)
        }
        val today2 = RunningDate().apply {
            setTimeType(TimeType.PM)
        }
        assertFalse(today > today2)
    }
}
