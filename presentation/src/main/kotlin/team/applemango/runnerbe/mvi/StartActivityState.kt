/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [StartActivityState.kt] created by Ji Sungbin on 22. 3. 20. 오전 1:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.mvi

import team.applemango.runnerbe.activity.StartActivity
import team.applemango.runnerbe.mvi.StartActivityState.Loaded
import team.applemango.runnerbe.mvi.StartActivityState.None

/**
 * [StartActivity] MVI state enum
 *
 * @property None 초기 값 (아무것도 하지 않음)
 * @property Loaded 로딩 완료 (data prefetch done)
 */
enum class StartActivityState {
    None,
    Loaded
}
