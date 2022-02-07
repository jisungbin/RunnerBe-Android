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
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.suspendCancellableCoroutine
import team.applemango.runnerbe.domain.repository.AccessTokenRepository
import kotlin.coroutines.resume

private const val NAVER_ACCESS_TOKEN_NULL = "Naver access token is null."
private const val RESPONSE_NOTHING = "Kakao API response is nothing."

class KakaoAccessTokenRepositoryImpl(private val context: Context) : AccessTokenRepository {
    override suspend fun getKakao(): String {
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

    override suspend fun getNaver(): String = suspendCancellableCoroutine { continuation ->
        NaverIdLoginSDK.authenticate(
            context,
            object : OAuthLoginCallback {
                override fun onSuccess() {
                    continuation.resume(
                        NaverIdLoginSDK.getAccessToken() ?: throw Exception(NAVER_ACCESS_TOKEN_NULL)
                    )
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    logeukes(type = LoggerType.E) { listOf(errorCode, errorDescription) }
                    throw Exception(errorDescription)
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }
        )
    }
}
