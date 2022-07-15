/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 2. 11. 오후 8:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "team.applemango.runnerbe.xml.photoeditor"
}

dependencies {
    implementation(Dependencies.EachKtx.Core)
    implementation(Dependencies.EachUi.PhotoEditor)
    implementation(Dependencies.EachUi.ConstraintLayout)
}
