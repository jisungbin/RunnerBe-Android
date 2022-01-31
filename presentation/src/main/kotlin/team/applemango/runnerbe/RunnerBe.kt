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
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class RunnerBe : Application() {
    override fun onCreate() {
        super.onCreate()

        // TODO: Set Exception Activity
        Erratum.setup(application = this)

        if (BuildConfig.DEBUG) {
            Logeukes.setup()
        }
    }
}
