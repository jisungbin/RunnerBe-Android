/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BaseResult.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.common

import team.applemango.runnerbe.domain.runningitem.common.BaseResult.NotYetVerify
import team.applemango.runnerbe.domain.runningitem.common.BaseResult.Success

/**
 * 추후 다양한 result 를 확장하여 지원할 수 있도록 interface 로 설계
 *
 * @property Success 작업 성공
 * @property NotYetVerify 아직 인증되지 않은 계정
 */
interface BaseResult {
    object Success : BaseResult
    object NotYetVerify : BaseResult
}
