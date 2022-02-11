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

dependencies {
    implementation("androidx.core:core-ktx:${Versions.Ktx.Core}")
    implementation("com.burhanrashid52:photoeditor:${Versions.Ui.PhotoEditor}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}")
}
