/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 22. 2. 24. 오후 10:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.model.runningitem.information

import team.applemango.runnerbe.domain.runningitem.model.runner.Runner
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem

/**
 * @property isMyItem 내가 올린 러닝 아이템인지 여부 (owner boolean)
 * @property bookmarked 내가 북마크 한 러닝 아이템인지 여부
 * @property item 러닝 아이템 상새 정보
 * @property joinRunners 참여한 러너들
 * @property waitingRunners 참여 신청한 러너들
 */
data class RunningItemInformation(
    val isMyItem: Boolean,
    val bookmarked: Boolean,
    val item: RunningItem,
    val joinRunners: List<Runner>,
    val waitingRunners: List<Runner>,
)
