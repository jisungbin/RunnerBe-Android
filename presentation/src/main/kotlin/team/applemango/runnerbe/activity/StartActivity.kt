/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

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

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                logeukes { listOf("pendingDynamicLinkData", pendingDynamicLinkData) }
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link
                    logeukes {
                        listOf(
                            "deepLink",
                            deepLink,
                            pendingDynamicLinkData.utmParameters,
                            pendingDynamicLinkData.extensions
                        )
                    }
                }
            }
            .addOnFailureListener(this) {
                logeukes { "getDynamicLink:onFailure: $it" }
            }

        fun getVerifyCodeSettings(uuid: String) = actionCodeSettings {
            url = "https://runnerbe-auth.shop/test" // 이 값은 입력받는 이메일과 항상 일치해야 함
            handleCodeInApp = true // 필수!
            setAndroidPackageName(
                "team.applemango.runnerbe", // 리다이렉트될 앱 패키지명
                true, // 이용 불가능시 플레이스토어 이동해서 설치 요청
                "21" // min sdk level
            )
        }

        val actionCodeSettings = actionCodeSettings {
            url = "https://runnerbe-auth.shop/test" // 이 값은 입력받는 이메일과 항상 일치해야 함
            handleCodeInApp = true // 필수!
            setAndroidPackageName(
                "team.applemango.runnerbe", // 리다이렉트될 앱 패키지명
                true, // 이용 불가능시 플레이스토어 이동해서 설치 요청
                "21" // min sdk level
            )
        }

        // Firebase.auth.signOut()
        logeukes { "asdsadfawfsc" }
        Firebase.auth
            .sendSignInLinkToEmail(
                "dahaeng.service@gmail.com",
                actionCodeSettings
            )
            .addOnSuccessListener {
                logeukes { "sent" }
            }
            .addOnFailureListener { exception ->
                logeukes { exception }
            }

        Firebase.auth.createUserWithEmailAndPassword("sungbin.me@gmail.com", "1234567890")
            .addOnSuccessListener {
                logeukes { "created" }
                Firebase.auth.currentUser?.let {
                    it.sendEmailVerification().addOnSuccessListener {
                        logeukes { "sent 2" }
                    }.addOnFailureListener {
                        logeukes { "eee: $it" }
                    }
                } ?: run {
                    logeukes { "null" }
                }
            }
            .addOnFailureListener {
                logeukes { "AAAL : $it" }
            }



        /*// 무조건 다른 액티비티로 이동되므로 알아서 cancel 됨 (수동 cancel 불필요)
        applicationContext.dataStore.data.collectWithLifecycle(this) { preferences ->
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
