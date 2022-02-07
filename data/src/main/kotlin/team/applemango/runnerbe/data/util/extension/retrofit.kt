/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [retrofit.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.extension

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.jackson.JacksonConverterFactory

internal fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    for (interceptor in interceptors) builder.addInterceptor(interceptor)
    return builder.build()
}

internal val JacksonConverter = JacksonConverterFactory.create(mapper)
