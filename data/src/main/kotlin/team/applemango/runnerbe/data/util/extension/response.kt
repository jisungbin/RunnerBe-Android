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
import team.applemango.runnerbe.data.common.BaseResponse
import team.applemango.runnerbe.data.register.login.model.login.LoginRequestResponse

private const val REQUEST_EXCEPTION =
    "The request is a success, but the server execution is failed. (or result field is null)"

internal fun <T : BaseResponse> Response<T>.requireSuccessfulBody(
    requestName: String,
    resultVerifyBuilder: (body: T) -> Boolean,
): T {
    val body = body()
    return if (isSuccessful && body != null && resultVerifyBuilder(body)) {
        body
    } else {
        throw Exception(
            """
            Request $requestName is fail.
            Http message: ${errorBody()?.use { it.string() }}
            Server message: ${body()?.message}
            """.trimIndent()
        )
    }
}

internal fun <T> Response<T>.requireSuccessfulBody(
    requestName: String,
    resultVerifyBuilder: (body: T) -> Boolean,
): T {
    val body = body()
    return if (isSuccessful && body != null && resultVerifyBuilder(body)) {
        body
    } else {
        throw Exception(
            """
            Request $requestName is fail.
            Http message: ${errorBody()?.use { it.string() }}
            """.trimIndent()
        )
    }
}
