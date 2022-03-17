/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLogin.kt] created by Ji Sungbin on 22. 2. 5. 오후 10:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.repository

import android.app.Activity
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import team.applemango.runnerbe.domain.register.runnerbe.repository.AccessTokenRepository

private val EXCEPTION_NAVER_ACCESS_TOKEN_NULL = Exception("Naver access token is null.")
private val EXCEPTION_RESPONSE_NOTHING = Exception("Kakao API response is nothing.")

// must be activity context
// TODO: https://github.com/runner-be/RunnerBe-Android/issues/56
// Activity usage.
class AccessTokenRepositoryImpl(private val activityContext: Activity) : AccessTokenRepository {
    override suspend fun getKakao(): String {
        return if (UserApiClient.instance.isKakaoTalkLoginAvailable(activityContext)) {
            loginWithKakaoTalk(activityContext)
        } else {
            loginWithWebView(activityContext)
        }
    }

    private suspend fun loginWithKakaoTalk(activityContext: Activity): String {
        return suspendCancellableCoroutine<Result<String>> { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(activityContext) { token, error ->
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(token.accessToken)
                        else -> failure(EXCEPTION_RESPONSE_NOTHING)
                    }
                )
            }
        }.getOrThrow()
    }

    private suspend fun loginWithWebView(activityContext: Activity): String {
        return suspendCancellableCoroutine<Result<String>> { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(activityContext) { token, error ->
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(token.accessToken)
                        else -> failure(EXCEPTION_RESPONSE_NOTHING)
                    }
                )
            }
        }.getOrThrow()
    }

    override suspend fun getNaver(): String {
        return suspendCancellableCoroutine<Result<String>> { continuation ->
            NaverIdLoginSDK.authenticate(
                activityContext,
                object : OAuthLoginCallback {
                    override fun onSuccess() {
                        val token = NaverIdLoginSDK.getAccessToken()
                        continuation.resume(
                            token?.let { success(it) }
                                ?: failure(EXCEPTION_NAVER_ACCESS_TOKEN_NULL)
                        )
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        continuation.resume(failure(Exception("$httpStatus ($message)")))
                    }

                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }
                }
            )
        }.getOrThrow()
    }
}
