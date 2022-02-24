/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:25
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {
    Dependencies.SharedKtx.forEach(::api)
    api(Dependencies.Jetpack.DataStore)
    api(project(ProjectConstants.Data))
    api(project(ProjectConstants.Domain))
    api(project(ProjectConstants.SharedDomain))
    implementation(Dependencies.EachKtx.Activity)
}
