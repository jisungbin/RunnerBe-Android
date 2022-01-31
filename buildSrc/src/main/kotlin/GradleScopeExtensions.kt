/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [GradleScopeExtensions.kt] created by Ji Sungbin on 22. 1. 31. 오후 9:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

fun DependencyHandler.installSharedHiltJUnit5(isSharedModule: Boolean = false) {
    if (!isSharedModule) {
        implementationProject(ProjectConstants.Shared)
    }
    add("implementation", Dependencies.Hilt)
    add("testDebugImplementation", Dependencies.Test.JunitApi)
    add("testDebugRuntimeOnly", Dependencies.Test.JunitEngine)
    add("testDebugImplementation", Dependencies.Test.Hamcrest)
    add("testDebugImplementation", Dependencies.Test.Coroutine)
    add("kapt", Dependencies.Compiler.Hilt)
}

fun PluginDependenciesSpec.installLibraryKotlinKaptHiltJUnit5(isLibrary: Boolean = true) {
    if (isLibrary) {
        id("com.android.library")
    }
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
}

fun DependencyHandler.implementationProject(path: String) {
    add("implementation", project(path))
}

private fun DependencyHandler.project(path: String): ProjectDependency =
    project(mapOf(Pair("path", path))) as ProjectDependency
