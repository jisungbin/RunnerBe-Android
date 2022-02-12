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
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.log.NidLog
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class RunnerBe : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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
        } else {
            Erratum.setup(application = this) // TODO: Set Exception Activity
        }
    }
}
