/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.dokka")
}

tasks.register<org.jetbrains.dokka.gradle.DokkaTask>("dokkaCustomFormat") {
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase"
                to
                    """{ "footerMessage": "made with ❤ by <a href="https://github.com/jisungbin">@jisungbin</a>" }"""
        )
    )
}

tasks.dokkaHtml.configure {
    moduleName.set("RunnerBe-Android: Domain")
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(true)
            skipDeprecated.set(false)
            skipEmptyPackages.set(false)
            jdkVersion.set(11)
        }
    }
}
