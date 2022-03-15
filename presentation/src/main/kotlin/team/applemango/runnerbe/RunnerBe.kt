/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerBe.kt] created by Ji Sungbin on 22. 1. 31. 오후 6:56
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.github.anrwatchdog.ANRWatchDog
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.log.NidLog
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class RunnerBe : Application() {

    companion object {
        var remoteConfigReady = false
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Firebase.analytics
        Firebase.crashlytics
        ANRWatchDog()
            .setIgnoreDebugger(true)
            .setANRListener { error ->
                Firebase.crashlytics.recordException(error)
                throw error
            }
            .start()

        NidLog.init()
        NaverIdLoginSDK.initialize(
            applicationContext,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            getString(R.string.app_name)
        )
        KakaoSdk.init(applicationContext, BuildConfig.KAKAO_API_KEY)

        if (BuildConfig.DEBUG) {
            Logeukes.setup()
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 60 * 10 // 10분
            }
            Firebase.remoteConfig.setConfigSettingsAsync(configSettings)
        }

        /*Erratum.setup(
            application = this,
            registerExceptionActivityIntent = { thread, throwable, lastActivity ->
                Firebase.crashlytics.recordException(throwable)
                logeukes(type = LoggerType.E) { throwable }
                Intent(
                    lastActivity,
                    DefaultErratumExceptionActivity::class.java
                )
            }
        )*/

        Firebase.remoteConfig
            .fetchAndActivate()
            .addOnSuccessListener {
                remoteConfigReady = true
            }
            .addOnFailureListener { exception ->
                Firebase.crashlytics.recordException(exception)
            }
    }
}
