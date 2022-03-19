/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteState.kt] created by Ji Sungbin on 22. 3. 19. 오후 11:30
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.mvi

import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState.Done
import team.applemango.runnerbe.feature.home.write.mvi.RunningItemWriteState.None

/**
 * 러닝 아이템 작성 MVI state model
 *
 * @property None 초기 상태
 * @property Done 등록 완료 상태
 */
sealed class RunningItemWriteState {
    object None : RunningItemWriteState()
    object Done : RunningItemWriteState()
}
