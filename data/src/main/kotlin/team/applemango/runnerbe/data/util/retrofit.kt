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
import team.applemango.runnerbe.data.main.api.MainService
import team.applemango.runnerbe.data.register.login.api.LoginService
import team.applemango.runnerbe.data.register.login.api.RegisterService
import team.applemango.runnerbe.data.register.mailjet.api.MailjetService
import team.applemango.runnerbe.data.secret.Mailjet
import team.applemango.runnerbe.data.secret.RunnerbeHost
import team.applemango.runnerbe.data.util.extension.mapper
import team.applemango.runnerbe.data.util.interceptor.BasicAuthInterceptor

private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    for (interceptor in interceptors) builder.addInterceptor(interceptor)
    return builder.build()
}

private val JacksonConverter = JacksonConverterFactory.create(mapper)

private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor { message ->
    if (message.isNotEmpty()) {
        logeukes("OkHttp") { message }
    }
}.apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val runnerbeBaseApi = Retrofit.Builder()
    .baseUrl(RunnerbeHost)
    .addConverterFactory(JacksonConverter)
    .client(getInterceptor(getHttpLoggingInterceptor()))
    .build()

private val mailjetBaseApi = Retrofit.Builder()
    .baseUrl(Mailjet.Host)
    .addConverterFactory(JacksonConverter)
    .client(
        getInterceptor(
            getHttpLoggingInterceptor(),
            BasicAuthInterceptor(Mailjet.ApiKey, Mailjet.SecretKey)
        )
    )
    .build()

internal val loginApi = runnerbeBaseApi.create(LoginService::class.java)
internal val registerApi = runnerbeBaseApi.create(RegisterService::class.java)
internal val mailjetApi = mailjetBaseApi.create(MailjetService::class.java)

internal val mainApi = runnerbeBaseApi.create(MainService::class.java)
