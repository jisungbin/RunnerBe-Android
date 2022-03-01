/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [JobChangeResult.kt] created by Ji Sungbin on 22. 3. 1. 오전 10:28
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.constant

import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.user.constant.JobChangeResult.NotYetAvailable

/**
 * @property NotYetAvailable 아직 3개월이 지나지 않아 변경할 수 없음
 */
sealed class JobChangeResult : BaseResult {
    object NotYetAvailable : JobChangeResult()
}