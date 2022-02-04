/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 2. 1. 오전 11:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
    installLibraryDfmHiltTestScabbard(isLibrary = false)
}

android {
    defaultConfig {
        versionCode = ApplicationConstants.versionCode
        versionName = ApplicationConstants.versionName
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    dynamicFeatures += setOf(
        ProjectConstants.RegisterSnsLogin,
        ProjectConstants.RegisterInformation
    )
}

dependencies {
    val features = listOf(
        ProjectConstants.Mail,
        ProjectConstants.MyPage,
        ProjectConstants.HomeBoard
    )

    features.forEach(::implementationProject)
    implementation(Dependencies.Firebase.Analytics)
    implementation(platform(Dependencies.Firebase.Bom))

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Debug.forEach(::debugImplementation)

    installSharedComposeHiltTest()
}
