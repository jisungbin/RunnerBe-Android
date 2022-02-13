/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BasicAuthInterceptor.java] created by Ji Sungbin on 22. 2. 13. 오후 9:41
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class BasicAuthInterceptor(user: String?, password: String?) : Interceptor {
    private val credentials = Credentials.basic(user!!, password!!)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}
