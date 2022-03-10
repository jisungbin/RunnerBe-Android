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
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import team.applemango.runnerbe.data.register.login.api.RegisterApi
import team.applemango.runnerbe.data.register.mailjet.api.MailjetApi
import team.applemango.runnerbe.data.runningitem.api.RunningItemApi
import team.applemango.runnerbe.data.secret.Mailjet
import team.applemango.runnerbe.data.secret.Runnerbe
import team.applemango.runnerbe.data.user.api.UserApi
import team.applemango.runnerbe.data.util.extension.JacksonConverter
import team.applemango.runnerbe.data.util.interceptor.BasicAuthInterceptor
import team.applemango.runnerbe.shared.domain.dsl.RunnerbeDsl

internal data class ClientModel(
    var interceptors: List<Interceptor> = emptyList(),
    var builder: OkHttpClient.Builder.() -> OkHttpClient.Builder = { this },
)

private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor { message ->
    if (message.isNotEmpty()) {
        logeukes("OkHttp") { message }
    }
}.apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun OkHttpClient.Builder.setTimeout(second: Long = 5) =
    connectTimeout(second, TimeUnit.SECONDS)
        .readTimeout(second, TimeUnit.SECONDS)
        .writeTimeout(second, TimeUnit.SECONDS)

private fun buildClient(@RunnerbeDsl builder: ClientModel.() -> Unit): OkHttpClient {
    val clientModel = ClientModel().apply(builder)
    val client = OkHttpClient.Builder().apply {
        clientModel.builder
    }
    for (interceptor in clientModel.interceptors) {
        client.addInterceptor(interceptor)
    }
    return client.build()
}

private val runnerbeBaseApi = Retrofit.Builder()
    .baseUrl(Runnerbe.Host)
    .addConverterFactory(JacksonConverter)
    .client(
        buildClient {
            builder = {
                setTimeout()
            }
            interceptors = listOf(getHttpLoggingInterceptor())
        }
    )
    .build()

private val mailjetBaseApi = Retrofit.Builder()
    .baseUrl(Mailjet.Host)
    .addConverterFactory(JacksonConverter)
    .client(
        buildClient {
            builder = {
                setTimeout()
            }
            interceptors = listOf(
                getHttpLoggingInterceptor(),
                BasicAuthInterceptor(Mailjet.ApiKey, Mailjet.SecretKey)
            )
        }
    )
    .build()

internal val registerApi = runnerbeBaseApi.create(RegisterApi::class.java)
internal val mailjetApi = mailjetBaseApi.create(MailjetApi::class.java)

internal val userApi = runnerbeBaseApi.create(UserApi::class.java)
internal val runningItemApi = runnerbeBaseApi.create(RunningItemApi::class.java)
