/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLogin.kt] created by Ji Sungbin on 22. 2. 5. 오후 10:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import team.applemango.runnerbe.domain.repository.AccessTokenRepository
import kotlin.coroutines.resume

private const val RESPONSE_NOTHING = "Kakao API response is nothing."

class KakaoAccessTokenRepositoryImpl(private val context: Context) : AccessTokenRepository {
    override suspend fun getAccessToken(): String {
        return if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context)
        } else {
            loginWithWebView(context)
        }
    }

    private suspend fun loginWithKakaoTalk(context: Context): String =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> throw Exception(error)
                        token != null -> token.accessToken
                        else -> throw Exception(RESPONSE_NOTHING)
                    }
                )
            }
        }

    private suspend fun loginWithWebView(context: Context): String =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> throw Exception(error)
                        token != null -> token.accessToken
                        else -> throw Exception(RESPONSE_NOTHING)
                    }
                )
            }
        }
}
