/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [retrofit.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util

import io.github.jisungbin.logeukes.logeukes
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import team.applemango.runnerbe.data.login.api.LoginService
import team.applemango.runnerbe.data.secret.HOST
import team.applemango.runnerbe.data.util.extension.mapper

internal fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    for (interceptor in interceptors) builder.addInterceptor(interceptor)
    return builder.build()
}

internal val JacksonConverter = JacksonConverterFactory.create(mapper)

internal fun provideHttpLoggingInterceptor() =
    HttpLoggingInterceptor { message -> logeukes("OkHttp") { message } }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

internal val loginApi = Retrofit.Builder()
    .baseUrl(HOST)
    .addConverterFactory(JacksonConverter)
    .client(getInterceptor(provideHttpLoggingInterceptor()))
    .build()
    .create(LoginService::class.java)
