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
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

// plugin
fun PluginDependenciesSpec.installLibraryDfmHiltTestScabbard(
    isLibrary: Boolean = true,
    isDFM: Boolean = false,
) {
    if (isLibrary && !isDFM) {
        id("com.android.library")
    }
    if (isDFM) {
        id("com.android.dynamic-feature")
    }
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
    id("scabbard.gradle") version Versions.Util.Scabbard
}

// dependencies
fun DependencyHandler.installSharedComposeHiltTest(
    isSharedModule: Boolean = false,
    excludeCompose: Boolean = false,
) {
    if (!isSharedModule) {
        implementationProject(ProjectConstants.Shared)
    }
    if (!excludeCompose) {
        Dependencies.Compose.forEach(::implementation)
    }
    implementation(Dependencies.Hilt)
    add("testDebugImplementation", Dependencies.Test.JunitApi)
    add("testDebugRuntimeOnly", Dependencies.Test.JunitEngine)
    add("testDebugImplementation", Dependencies.Test.Hamcrest)
    add("testDebugImplementation", Dependencies.Test.Coroutine)
    add("kapt", Dependencies.Compiler.Hilt)
}

fun DependencyHandler.implementationProject(path: String) {
    add("implementation", project(path))
}

private fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

private fun DependencyHandler.project(path: String) =
    project(mapOf(Pair("path", path))) as ProjectDependency
