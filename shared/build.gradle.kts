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
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

// data 로 부터 의존성 주입 받아서 domain 으로 Provides 해줌
dependencies {
    api(Dependencies.Util.Logeukes)
    api(Dependencies.Jetpack.DataStore)
    api(project(ProjectConstants.Data))
    api(project(ProjectConstants.Domain))

    implementation(Dependencies.Di.Hilt)
    implementationProject(ProjectConstants.Data)

    kapt(Dependencies.Compiler.Hilt)
    Dependencies.SharedKtx.forEach(::api)
}
