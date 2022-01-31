/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    installKaptWithHiltPlugin()
}

dependencies {
    implementation(projects.shared)
    // TODO: SNS 로그인 (카카오, 네이버, 애플)

    installHilt()
}
