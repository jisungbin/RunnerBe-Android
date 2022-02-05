/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NaverLoginImpl.kt] created by Ji Sungbin on 22. 2. 6. 오전 12:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.suspendCancellableCoroutine
import team.applemango.runnerbe.domain.repository.LoginRepository
import kotlin.coroutines.resume

private const val TOKEN_NULL = "Response is null."

class NaverLoginRepositoryImpl(private val context: Context) : LoginRepository {
    override suspend fun getAccessToken(): String = suspendCancellableCoroutine { continuation ->
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