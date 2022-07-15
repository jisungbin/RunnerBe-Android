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
    id("org.jetbrains.dokka") version Versions.BuildUtil.Dokka
    id("de.mannodermaus.android-junit5")
    jacoco
}

android {
    namespace = "team.applemango.runnerbe.domain"
}

jacoco {
    toolVersion = Versions.Test.JaCoCo
}

tasks.withType<JacocoReport> {
    reports {
        html.required.set(true)
        html.outputLocation.set(layout.projectDirectory.dir("../documents/coverage/jacoco/html"))
        xml.required.set(true) // codecov depends on xml format report
        xml.outputLocation.set(layout.projectDirectory.file("../documents/coverage/jacoco/report.xml"))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

dependencies {
    implementationProject(ProjectConstants.SharedDomain)

    testRuntimeOnly(Dependencies.Test.JunitEngine)
    testImplementation(Dependencies.Test.JunitApi)
    testImplementation(Dependencies.Test.Hamcrest)
    testImplementation(Dependencies.Test.Coroutine)
}
