/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import io.github.jisungbin.logeukes.logeukes

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // 로그인하려면 이 이메일 주소가 처음에 로그인 링크를 보낸 주소와 일치해야 합니다.
        val verifyCodeSettings = actionCodeSettings {
            url = "https://runnerbe-auth.shop/byeworld"
            handleCodeInApp = true
            setAndroidPackageName(
                "team.applemango.runnerbe",
                true,
                "21"
            )
        }

        logeukes { "AAA!!!!" }
        logeukes { "sungbin.me@gmail.com" }
        Firebase.auth.sendSignInLinkToEmail("sungbin.me@gmail.com", verifyCodeSettings)
            .addOnCompleteListener { task ->
                logeukes { "SENT!" }
                if (task.isSuccessful) {
                    logeukes { "SENT SUCCESSFUL!" }
                } else {
                    logeukes { "SENT NOT SUCCESSFUL!" }
                }
            }
            .addOnSuccessListener {
                logeukes { "SUCCESS!" }
            }
            .addOnFailureListener {
                logeukes { "FAILURE!: $it" }
            }

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                logeukes { pendingDynamicLinkData }
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    logeukes { deepLink }
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

                // ...
            }
            .addOnFailureListener(this) {
                logeukes { "getDynamicLink:onFailure: $it" }
            }

        // 무조건 다른 액티비티로 이동되므로 알아서 cancel 됨 (수동 cancel 불필요)
        /*applicationContext.dataStore.data.collectWithLifecycle(this) { preferences ->
            val isSignedUser = preferences[DataStoreKey.Login.Jwt] != null
            val isSnsLoginDone = preferences[DataStoreKey.Login.Uuid] != null
            when {
                isSignedUser -> { // JWT 존재
                    changeActivityWithAnimation<BoardActivity>() // XXX
                    // 얜 fragment 이여야 함
                    return@collectWithLifecycle
                }
                isSnsLoginDone -> { // SNS 로그인 완료 -> 온보딩 페이지로 이동
                    changeActivityWithAnimation<DFMOnboardActivityAlias>()
                    return@collectWithLifecycle
                }
                !isSnsLoginDone -> { // SNS 로그인 하기 전
                    changeActivityWithAnimation<DFMLoginActivityAlias>()
                    return@collectWithLifecycle
                }
            }
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.animate().run {
                    alpha(0f)
                    scaleX(0f)
                    scaleY(0f)
                    interpolator = AnticipateInterpolator()
                    duration = 200L
                    withEndAction { splashScreenView.remove() }
                    withLayer()
                    start()
                }
            }
        }
    }
}
