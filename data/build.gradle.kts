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
    id("com.dicedmelon.gradle.jacoco-android")
}

jacoco {
    toolVersion = "0.8.7"
}

jacocoAndroidUnitTestReport {
    csv.enabled(true)
    xml.enabled(true)
    html.enabled(true)
}

/*tasks.withType<Test> {
    jacoco.includeNoLocationClasses = true
}*/

dependencies {
    Dependencies.Login.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    implementationProject(ProjectConstants.Domain)
    implementationProject(ProjectConstants.SharedDomain)

    implementation(platform(Dependencies.FirebaseBom))
    implementation(Dependencies.FirebaseEachKtx.Storage)

    testDebugImplementation(Dependencies.Test.JunitApi)
    testDebugRuntimeOnly(Dependencies.Test.JunitEngine)
    testDebugImplementation(Dependencies.Test.Hamcrest)
    testDebugImplementation(Dependencies.Test.Coroutine)
}
