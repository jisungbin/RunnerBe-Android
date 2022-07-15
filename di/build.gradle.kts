/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 3. 18. 오전 1:17
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "team.applemango.runnerbe.di"
}

dependencies {
    implementationProject(ProjectConstants.Data)
    implementationProject(ProjectConstants.Domain)
    implementation(Dependencies.Jetpack.Hilt)
    kapt(Dependencies.Compiler.Hilt)
}
