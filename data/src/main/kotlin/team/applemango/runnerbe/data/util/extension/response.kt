/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [response.kt] created by Ji Sungbin on 22. 2. 7. 오후 8:00
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.extension

import retrofit2.Response
import team.applemango.runnerbe.data.login.model.LoginRequestResponse

internal const val REQUEST_EXCEPTION =
    "The request is a success, but the server execution is failed. (or result field is null)"

internal fun Response<LoginRequestResponse>.requireResponse(requestName: String): LoginRequestResponse {
    val body = body()
    if (isSuccessful && body != null) {
        if (body.isSuccess == true && body.code in 1001..1002 && body.result != null) {
            return body
        } else {
            throw Exception(body.message ?: REQUEST_EXCEPTION)
        }
    } else {
        throw throw Exception("Request $requestName is fail. (${errorBody()?.use { it.string() }})")
    }
}
