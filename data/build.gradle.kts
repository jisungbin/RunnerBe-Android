/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:15
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

dependencies {
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)
    Dependencies.Login.All.forEach(::implementation)

    implementation(Dependencies.Coroutine)
    implementation(Dependencies.Util.Logeukes)
    implementation(project(ProjectConstants.Domain))

    testDebugImplementation(Dependencies.Test.JunitApi)
    testDebugRuntimeOnly(Dependencies.Test.JunitEngine)
    testDebugImplementation(Dependencies.Test.Hamcrest)
    testDebugImplementation(Dependencies.Test.Coroutine)
}
