/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Web.kt] created by Ji Sungbin on 22. 2. 8. 오후 9:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.util

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.shared.extension.toast

internal object Web {
    enum class Link(val string: String) {
        ServiceTerms("https://runnerbe.xyz/policy/service.txt"),
        LocateTerms("https://runnerbe.xyz/policy/location.txt"),
        PersonalInformationTerms("https://runnerbe.xyz/policy/privacy-collect.txt")
    }

    @Suppress("DEPRECATION")
    // None deprecated way usage: Didn't find class "androidx.browser.customtabs.CustomTabColorSchemeParams$Builder"
    // Issue: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/17
    fun open(context: Context, link: Link) {
        try {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(presentationColorOf(context, "primary"))
            builder.build()
                .intent
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val tabIntent = builder.build()
            tabIntent.launchUrl(context, link.string.toUri())
        } catch (exception: Exception) {
            logeukes(type = LoggerType.E) { exception }
            toast(context, StringAsset.Toast.NonInstallBrowser)
        }
    }
}
