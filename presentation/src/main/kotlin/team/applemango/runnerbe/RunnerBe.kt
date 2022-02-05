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
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class RunnerBe : Application() {
    override fun onCreate() {
        super.onCreate()
        Erratum.setup(application = this) // TODO: Set Exception Activity
        NaverIdLoginSDK.initialize(applicationContext, {OAUTH_CLIENT_ID}, {OAUTH_CLIENT_SECRET},getString(R.string.app_name))
        KakaoSdk.init(applicationContext, BuildConfig.KAKAO_API_KEY)
        if (BuildConfig.DEBUG) {
            Logeukes.setup()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}
