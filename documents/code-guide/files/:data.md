# :data

:domain 에서 작성한 파일들의 구현을 담당합니다. :domain 과 다르게 여러 의존성을 가지고 있습니다.

## 코루틴

Login SDK(AccessTokenRepositoryImpl 참고) 에서 result 를 콜백으로 주고 있습니다. 이를 동기처럼 한 번에 return 하는 함수로 구현하기 위해
suspendCancellableCoroutine 를 사용하였습니다.

---

### AccessTokenRepositoryImpl.kt

```kotlin
class AccessTokenRepositoryImpl(activityContext: Activity) : AccessTokenRepository {

    private val _activityContext = WeakReference(activityContext)
    private val activityContext get() = _activityContext.get()!!

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
```