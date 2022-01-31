/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:25
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("de.mannodermaus.android-junit5")
    installKaptWithHiltPlugin()
}

dependencies {
    api(Dependencies.Coroutine)

    Dependencies.Ktx.forEach(::api)
    Dependencies.Util.forEach(::api)
    //  Dependencies.Test.forEach(::testImplementation)
    testDebugApi("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testDebugApi("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    installHilt()
}
