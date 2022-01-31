/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:25
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    installLibraryKotlinKaptHiltJUnit5()
}

dependencies {
    api(Dependencies.Coroutine)
    api(project(ProjectConstants.Domain))

    Dependencies.Ktx.forEach(::api)
    Dependencies.Util.forEach(::api)

    installSharedHiltJUnit5(isSharedModule = true)
}
