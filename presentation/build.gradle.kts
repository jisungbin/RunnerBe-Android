/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 2. 1. 오전 11:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    // id("com.spotify.ruler")
    installLibraryDfmHiltTest(isPresentation = true)
    id("com.android.application")
    id("com.google.gms.google-services")
    // id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("name.remal.check-dependency-updates") version Versions.BuildUtil.CheckDependencyUpdates
}

/*ruler {
    abi.set("arm64-v8a")
    locale.set("ko")
    screenDensity.set(480)
    sdkVersion.set(31)
}*/

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
        debug {
            aaptOptions.cruncherEnabled = false // png optimization (default: true)
        }

        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    dynamicFeatures.addAll(
        setOf(
            ProjectConstants.RegisterSnsLogin,
            ProjectConstants.RegisterOnboard
        )
    )
}

dependencies {
    val features = listOf(
        ProjectConstants.Mail,
        ProjectConstants.SharedAndroid,
        ProjectConstants.MyPage,
        ProjectConstants.HomeBoard
    )
    features.forEach(::implementationProject)

    implementation(Dependencies.Util.Erratum)
    implementation(Dependencies.EachUi.Material)
    implementation(platform(Dependencies.FirebaseBom))
    implementation(Dependencies.FirebaseEachKtx.Analytics)
    implementation(Dependencies.FirebaseEachKtx.Performance)
    // implementation(Dependencies.FirebaseEachKtx.Crashlytics)
    implementation(Dependencies.FirebaseEachKtx.RemoteConfig)
    implementation("dev.chrisbanes.insetter:insetter:0.6.1")
    implementation("dev.chrisbanes.insetter:insetter-dbx:0.6.1")

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Login.forEach(::implementation)
    Dependencies.Location.forEach(::implementation)
    Dependencies.Analytics.forEach(::implementation)
    Dependencies.Jetpack.Navigation.forEach(::implementation)
    Dependencies.PresentationOnlyKtx.forEach(::implementation)

    debugImplementation(Dependencies.Debug.LeakCanary)
    installSharedComposeOrbitHiltTest(excludeCompose = true)
}
