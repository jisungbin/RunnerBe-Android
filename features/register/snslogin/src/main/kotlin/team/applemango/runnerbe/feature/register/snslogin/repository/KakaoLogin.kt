/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:15
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.snslogin.repository

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

private const val RESPONSE_NOTHING = "Kakao API response is nothing."

@ViewModelScoped
internal class KakaoLogin @Inject constructor() {
    suspend operator fun invoke(context: Context): Long {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context)
        } else {
            loginWithWebView(context)
        }
        return getUser().id ?: throw Exception("User id is null.")
    }

    private suspend fun loginWithKakaoTalk(context: Context): Unit =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> throw Exception(error)
                        token != null -> Unit
                        else -> throw Exception(RESPONSE_NOTHING)
                    }
                )
            }
        }

    private suspend fun loginWithWebView(context: Context): Unit =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> throw Exception(error)
                        token != null -> Unit
                        else -> throw Exception(RESPONSE_NOTHING)
                    }
                )
            }
        }

    private suspend fun getUser(): User = suspendCancellableCoroutine { continuation ->
        UserApiClient.instance.me { user, error ->
            continuation.resume(
                when {
                    error != null -> throw error
                    user != null -> user
                    else -> throw Throwable(RESPONSE_NOTHING)
                }
            )
        }
    }
}
