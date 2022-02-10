/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 9:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    installLibraryDfmHiltTestScabbard(isDFM = true)
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Main
    }
}

dependencies {
    implementation(Dependencies.Browser)
    implementation(platform(Dependencies.Firebase.Bom))
    implementation(Dependencies.Firebase.Auth)
    implementation(Dependencies.Firebase.GoogleServiceAuth)
    implementationProject(ProjectConstants.HomeBoard)
    implementationProject(ProjectConstants.Presentation)
    implementationProject(ProjectConstants.XmlNumberPicker)
    installSharedComposeHiltTest()
}
