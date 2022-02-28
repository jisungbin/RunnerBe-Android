/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NickNameChangeResult.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.constant

import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.user.constant.NicknameChangeResult.AlreadyChanged
import team.applemango.runnerbe.domain.user.constant.NicknameChangeResult.Duplicate

/**
 * @property AlreadyChanged 이미 예전에 닉네임을 변경한 적이 있음 (닉네임 변경은 최초 1회만 가능)
 * @property Duplicate 중복된 닉네임
 */
sealed class NicknameChangeResult : BaseResult {
    object AlreadyChanged : NicknameChangeResult()
    object Duplicate : NicknameChangeResult()
}
