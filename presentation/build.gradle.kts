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
    id("com.google.gms.google-services")
    id("com.google.android.gms.oss-licenses-plugin")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    installLibraryDfmHiltTestScabbard(isLibrary = false)
}

android {
    signingConfigs {
        create("release") {
            storeFile = file(BuildConstants.StoreFilePath)
            storePassword = BuildConstants.StorePassword
            keyAlias = BuildConstants.KeyAlias
            keyPassword = BuildConstants.KeyPassword
        }
    }

    defaultConfig {
        versionCode = ApplicationConstants.versionCode
        versionName = ApplicationConstants.versionName
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    dynamicFeatures += setOf(
        ProjectConstants.RegisterSnsLogin,
        ProjectConstants.RegisterOnboard
    )
}

dependencies {
    val features = listOf(
        ProjectConstants.Mail,
        ProjectConstants.Shared,
        ProjectConstants.MyPage,
        ProjectConstants.HomeBoard
    )
    features.forEach(::implementationProject)

    // onboard (dfm) 에서 필요하기 때문에 api 로 설정
    api(platform(Dependencies.Firebase.Bom))
    api(Dependencies.Firebase.Auth)
    api(Dependencies.Firebase.Analytics)
    api(Dependencies.Firebase.GoogleServiceAuth)

    // 얘는 presentation 에서만 사용
    implementation(Dependencies.Firebase.DynamicLink)

    implementation(Dependencies.Util.Erratum)
    Dependencies.Ui.forEach(::implementation)
    Dependencies.Login.All.forEach(::implementation)
    Dependencies.PresentationOnlyKtx.forEach(::implementation)

    Dependencies.Debug.forEach(::debugImplementation)
    installSharedComposeHiltTest(excludeCompose = true)
}
