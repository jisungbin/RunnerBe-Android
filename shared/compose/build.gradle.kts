/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 2. 4. 오후 11:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "team.applemango.runnerbe.shared.compose"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Main
    }
}

dependencies {
    implementation(Dependencies.EachKtx.Core)
    implementation(Dependencies.EachKtx.Lifecycle)
    implementationProject(ProjectConstants.Domain)
    implementationProject(ProjectConstants.SharedDomain)
    implementationProject(ProjectConstants.XmlRangePicker)
    implementationProject(ProjectConstants.XmlSuperWheelPicker)
    Dependencies.Compose.forEach(::implementation)
}
