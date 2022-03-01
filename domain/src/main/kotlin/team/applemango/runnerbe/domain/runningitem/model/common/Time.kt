/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Duration.kt] created by Ji Sungbin on 22. 3. 1. 오전 9:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.model.common

import java.text.DecimalFormat

data class Time(val hour: Int, val minute: Int, val second: Int) {
    private val formatter = DecimalFormat("00")
    override fun toString() =
        "${formatter.format(hour)}:${formatter.format(minute)}:${formatter.format(second)}"
}
