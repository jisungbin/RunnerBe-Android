/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NaverLogin.kt] created by Ji Sungbin on 22. 2. 6. 오전 12:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

private const val TOKEN_NULL = "Response is null."

@ViewModelScoped
internal class NaverLogin @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend operator fun invoke(): String = suspendCancellableCoroutine { continuation ->
        NaverIdLoginSDK.authenticate(
            context,
            object : OAuthLoginCallback {
                override fun onSuccess() {
                    continuation.resume(
                        NaverIdLoginSDK.getAccessToken() ?: throw Exception(TOKEN_NULL)
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
