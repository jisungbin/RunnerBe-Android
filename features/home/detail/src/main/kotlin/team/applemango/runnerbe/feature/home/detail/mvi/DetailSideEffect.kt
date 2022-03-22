/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DetailSideEffect.kt] created by Ji Sungbin on 22. 3. 22. 오후 11:29
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.detail.mvi

import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation

internal sealed class DetailSideEffect {
    data class LoadDone(val item: RunningItemInformation) : DetailSideEffect()
}
