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
    id("com.google.devtools.ksp") version Versions.Ksp
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    Dependencies.Login.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    implementationProject(ProjectConstants.Domain)
    implementationProject(ProjectConstants.SharedDomain)

    implementation(Dependencies.Ksp)
    implementation(Dependencies.Jetpack.Room)
    implementation(platform(Dependencies.FirebaseBom))
    implementation(Dependencies.FirebaseEachKtx.Storage)

    ksp(Dependencies.Compiler.RoomKsp)
}
