/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.R
import team.applemango.runnerbe.shared.di.test.TestRepo
import team.applemango.runnerbe.shared.util.extension.get
import team.applemango.runnerbe.shared.util.extension.set
import team.applemango.runnerbe.shared.util.extension.toast
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var testPref: SharedPreferences

    @Inject
    lateinit var testRepo: TestRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // TODO: Splash Theme
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (testPref["TEST"] == null) {
            toast("EMPTY!")
            testPref["TEST"] = "BYE"
        } else {
            toast(testPref["TEST"].orEmpty())
        }

        testRepo.print()
    }
}
