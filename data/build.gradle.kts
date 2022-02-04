/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:15
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

// Network, Test 의존성만 필요함
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("de.mannodermaus.android-junit5")
}

dependencies {
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    implementation(Dependencies.Coroutine)
    implementation(Dependencies.Util.Logeukes)
    implementationProject(ProjectConstants.Domain)

    testDebugImplementation(Dependencies.Test.JunitApi)
    testDebugRuntimeOnly(Dependencies.Test.JunitEngine)
    testDebugImplementation(Dependencies.Test.Hamcrest)
    testDebugImplementation(Dependencies.Test.Coroutine)
}
