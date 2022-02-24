/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BaseResult.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.common

/**
 * @property Success 작업 성공
 * @property NotYetVerify 아직 인증되지 않은 계정
 */
interface BaseResult {
    object Success : BaseResult
    object NotYetVerify : BaseResult
}
