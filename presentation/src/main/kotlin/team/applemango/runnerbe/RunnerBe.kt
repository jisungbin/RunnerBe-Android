package team.applemango.runnerbe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class RunnerBe : Application() {
    override fun onCreate() {
        super.onCreate()
        Erratum.setup(application = this)

        if (BuildConfig.DEBUG) {
            Logeukes.setup()
        }
    }
}
