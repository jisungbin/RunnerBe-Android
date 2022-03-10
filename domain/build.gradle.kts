/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.dokka") version Versions.Util.Dokka
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${Versions.Util.Dokka}")
    }
}

tasks.dokkaHtml.configure {
    moduleName.set("RunnerBe-Android: Domain")
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage =
            """made with <span style="color: orange;">❤</span> by <a href="https://github.com/jisungbin">@jisungbin</a>"""
        separateInheritedMembers = true
    }
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(true)
            skipDeprecated.set(false)
            skipEmptyPackages.set(false)
            jdkVersion.set(11)
        }
    }
}
