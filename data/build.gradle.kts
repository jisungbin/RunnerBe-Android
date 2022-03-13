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
    id("de.mannodermaus.android-junit5")
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.withType<JacocoReport> {
    reports {
        html.required.set(true)
        html.outputLocation.set(layout.projectDirectory.dir("documents/coverage/jacoco/html"))
        xml.required.set(true) // codecov depends on xml format report
        xml.outputLocation.set(layout.projectDirectory.file("documents/coverage/jacoco/report.xml"))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    Dependencies.Login.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    implementationProject(ProjectConstants.Domain)
    implementationProject(ProjectConstants.SharedDomain)

    implementation(platform(Dependencies.FirebaseBom))
    implementation(Dependencies.FirebaseEachKtx.Storage)

    testImplementation(Dependencies.Test.JunitApi)
    testRuntimeOnly(Dependencies.Test.JunitEngine)
    testImplementation(Dependencies.Test.Hamcrest)
    testImplementation(Dependencies.Test.Coroutine)
}
