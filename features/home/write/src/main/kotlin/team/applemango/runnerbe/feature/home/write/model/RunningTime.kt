/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningTime.kt] created by Ji Sungbin on 22. 3. 19. 오전 1:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.model

data class RunningTime(val hour: Int, val minute: Int) {
    override fun toString() = "$hour 시간 $minute 분"
}
