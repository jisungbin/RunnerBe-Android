/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BaseResponse.kt] created by Ji Sungbin on 22. 2. 24. 오후 4:31
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.common

interface BaseResponse {
    val code: Int?
    val message: String?
    val isSuccess: Boolean?
}
