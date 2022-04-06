/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DetailState.kt] created by Ji Sungbin on 22. 3. 22. 오후 11:08
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail.mvi

import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation
import team.applemango.runnerbe.feature.home.detail.mvi.DetailLoadState.Loaded
import team.applemango.runnerbe.feature.home.detail.mvi.DetailLoadState.Loading

/**
 * 러닝 아이템 상세 조회 상태
 *
 * @property Loading 로딩 중
 * @property Loaded 로드 완료
 */
internal sealed class DetailLoadState {
    object Loading : DetailLoadState()
    data class Loaded(val item: RunningItemInformation) : DetailLoadState()
}
