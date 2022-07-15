/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 4. 5. 오후 4:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    installLibraryDfmHiltTest()
}

android {
    namespace = "team.applemango.runnerbe.feature.home.filter"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Main
    }
}

dependencies {
    Dependencies.GoogleMap.forEach(::implementation)
    installSharedComposeOrbitHiltTest()
}
