/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BaseResult.kt] created by Ji Sungbin on 22. 2. 24. 오후 8:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.common

import team.applemango.runnerbe.domain.main.common.BaseResult.DatabaseError
import team.applemango.runnerbe.domain.main.common.BaseResult.Exception
import team.applemango.runnerbe.domain.main.common.BaseResult.NotYetVerify
import team.applemango.runnerbe.domain.main.common.BaseResult.Success

/**
 * @property Success 작업 성공
 * @property NotYetVerify 아직 인증되지 않은 계정
 * @property DatabaseError 서버 데이터베이스 에러
 * @property Exception 프론트엔드 데이터 처리 에러
 */
interface BaseResult {
    object Success : BaseResult
    object NotYetVerify : BaseResult
    object DatabaseError : BaseResult
    data class Exception(val code: Int) : BaseResult
}
