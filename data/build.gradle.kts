/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:15
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    installLibraryKotlinKaptHiltJUnit5()
}

dependencies {
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    installSharedHiltJUnit5()
}
