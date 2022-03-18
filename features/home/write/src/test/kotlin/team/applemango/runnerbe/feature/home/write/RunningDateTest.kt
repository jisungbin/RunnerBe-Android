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
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import team.applemango.runnerbe.feature.home.write.component.util.DateCache.plusDayAndCaching
import team.applemango.runnerbe.feature.home.write.model.RunningDate

class RunningDateTest {
    @Test
    @DisplayName("어제와 오늘의 RunningDate 를 비교했을 때 내일의 RunningDate 가 더 크게 나와야 함")
    fun compareToRunningDate() {
        val today = RunningDate()
        val tomorrow = RunningDate(Date().plusDayAndCaching(1))
        assert(today < tomorrow)
    }
}
