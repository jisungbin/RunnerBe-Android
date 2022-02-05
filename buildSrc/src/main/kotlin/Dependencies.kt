/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Dependencies.kt] created by Ji Sungbin on 22. 1. 31. 오후 10:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

object Versions {
    const val Dagger = "2.40.5"
    const val FirebaseBom = "29.0.4"

    object Essential {
        const val Gradle = "7.1.1"
        const val Kotlin = "1.6.10"
        const val Coroutines = "1.6.0"
        const val GoogleService = "4.3.3"
    }

    object Ktx {
        const val Core = "1.7.0"
        const val PlayCore = "1.8.1"
        const val LifeCycle = "2.4.0"
        const val Navigation = "2.4.0"
    }

    object Compose {
        const val Lottie = "4.2.2"
        const val Activity = "1.4.0"
        const val Landscapist = "1.4.5"
        const val Insets = "0.24.0-alpha"
        const val Master = "1.2.0-alpha01"
        const val Material = "1.2.0-alpha01"
        const val ConstraintLayout = "1.0.0"
        const val LifecycleViewModel = "2.4.0"
        const val Accompanist = "0.24.1-alpha"
    }

    object Ui {
        const val Material = "1.5.0"
        const val AppCompat = "1.4.1"
        const val Splash = "1.0.0-beta01"
    }

    object Network {
        const val OkHttp = "4.9.3"
        const val Retrofit = "2.9.0"
    }

    object Util {
        const val Erratum = "1.0.1"
        const val Logeukes = "1.0.0"
        const val Jackson = "2.13.1"
        const val Scabbard = "0.5.0"
        const val LeakCanary = "2.8.1"
        const val SecretsGradlePlugin = "2.0.0"
        const val CheckDependencyUpdates = "1.5.0"
        const val DependencyGraphGenerator = "0.7.0"
    }

    object Jetpack {
        const val Hilt = "2.40.5"
        const val DataStore = "1.0.0"
        // TODO: room (offline-mode support)
    }

    object Login {
        const val Kakao = "2.8.5"
        const val Naver = "5.0.0"
    }

    object OssLicense {
        const val Master = "17.0.0"
        const val Classpath = "0.10.4"
    }

    object Test {
        const val JUnit = "5.8.2"
        const val Hamcrest = "2.2"
        const val Coroutine = "1.6.0"
        const val JUnitGradle = "1.8.2.0"
    }
}

@Suppress("MemberVisibilityCanBePrivate")
object Dependencies {
    const val Coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"

    object Di {
        const val Dagger = "com.google.dagger:dagger:${Versions.Dagger}"
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Jetpack.Hilt}"
    }

    val SharedKtx = listOf(
        "androidx.core:core-ktx:${Versions.Ktx.Core}",
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Ktx.LifeCycle}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Ktx.LifeCycle}"
    )

    val PresentationOnlyKtx = listOf(
        "com.google.android.play:core-ktx:${Versions.Ktx.PlayCore}",
        "androidx.navigation:navigation-ui-ktx:${Versions.Ktx.Navigation}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.Ktx.Navigation}",
    )

    object Firebase {
        const val Bom = "com.google.firebase:firebase-bom:${Versions.FirebaseBom}"
        const val Analytics = "com.google.firebase:firebase-analytics"
    }

    val Compose = listOf(
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "com.airbnb.android:lottie-compose:${Versions.Compose.Lottie}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.material:material:${Versions.Compose.Material}",
        "com.google.accompanist:accompanist-insets:${Versions.Compose.Insets}",
        "com.github.skydoves:landscapist-glide:${Versions.Compose.Landscapist}",
        "com.google.accompanist:accompanist-insets:${Versions.Compose.Accompanist}",
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.Compose.Accompanist}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.LifecycleViewModel}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.ConstraintLayout}",
    )

    val Ui = listOf(
        "androidx.appcompat:appcompat:${Versions.Ui.AppCompat}",
        "androidx.core:core-splashscreen:${Versions.Ui.Splash}",
        "com.google.android.material:material:${Versions.Ui.Material}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Master}"
    )

    val Jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Util.Jackson}"
    )

    val Network = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}"
    )

    object Util {
        const val Erratum = "land.sungbin:erratum:${Versions.Util.Erratum}"
        const val Logeukes = "land.sungbin:logeukes:${Versions.Util.Logeukes}"
    }

    val Debug = listOf(
        "com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}"
    )

    object Jetpack {
        const val DataStore =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}"
    }

    object Compiler {
        const val Dagger = "com.google.dagger:dagger-compiler:${Versions.Dagger}"
        const val Hilt = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.Hilt}"
        // TODO: room (offline-mode support)
    }

    object Login {
        const val Kakao = "com.kakao.sdk:v2-user:${Versions.Login.Kakao}"
        const val Naver = "com.navercorp.nid:oauth:${Versions.Login.Naver}"
        val All = listOf(Kakao, Naver)
    }

    object Test {
        const val Hamcrest = "org.hamcrest:hamcrest:${Versions.Test.Hamcrest}"
        const val JunitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.Test.JUnit}"
        const val JunitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.Test.JUnit}"
        const val Coroutine =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.Coroutine}"
    }
}
