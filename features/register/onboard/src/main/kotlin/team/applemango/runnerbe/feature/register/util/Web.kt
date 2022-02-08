/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Web.kt] created by Ji Sungbin on 22. 2. 8. 오후 9:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.util

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import team.applemango.runnerbe.feature.register.asset.StringAsset
import team.applemango.runnerbe.shared.util.extension.toast

internal object Web {
    enum class Link(val string: String) {
        ServiceTerms("https://applemango-runnerbe.github.io/terms-of-service.txt"),
        LocateTerms("https://applemango-runnerbe.github.io/location-based-service.txt"),
        PersonalInformationTerms("https://applemango-runnerbe.github.io/personal-information-collection-and-usage-agreement.txt")
    }

    fun open(context: Context, link: Link) {
        try {
            val builder = CustomTabsIntent.Builder()
            builder.build()
                .intent
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val customTabIntent = builder.build()
            customTabIntent.launchUrl(context, link.string.toUri())
        } catch (exception: Exception) {
            logeukes(type = LoggerType.E) { exception }
            toast(context, StringAsset.Toast.NonInstallBrowser)
        }
    }
}
