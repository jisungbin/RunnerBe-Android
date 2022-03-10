/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

// 아무런 의존성도 필요 없음
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.dokka")
}

tasks.dokkaHtml.configure {
    outputDirectory.set(File("../documentation/dokka"))
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
