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

// plugin
fun PluginDependenciesSpec.installLibraryDfmHiltTest(
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
    if (!isDFM) {
        id("dagger.hilt.android.plugin")
    }
    id("de.mannodermaus.android-junit5")
    // id("scabbard.gradle") version Versions.Util.Scabbard
}

// dependencies
fun DependencyHandler.installSharedComposeOrbitHiltTest(
    isSharedModule: Boolean = false,
    useDagger: Boolean = false,
) {
    if (!isSharedModule) {
        implementationProject(ProjectConstants.Shared)
    }
    implementation(Dependencies.Orbit)
    Dependencies.Compose.forEach(::implementation)
    implementationProject(ProjectConstants.SharedCompose)
    add("testDebugImplementation", Dependencies.Test.JunitApi)
    add("testDebugRuntimeOnly", Dependencies.Test.JunitEngine)
    add("testDebugImplementation", Dependencies.Test.Hamcrest)
    add("testDebugImplementation", Dependencies.Test.Coroutine)
    if (!useDagger) {
        implementation(Dependencies.Di.Hilt)
        add("kapt", Dependencies.Compiler.Hilt)
    } else {
        implementation(Dependencies.Di.Dagger)
        add("kapt", Dependencies.Compiler.Dagger)
    }
}

fun DependencyHandler.implementationProject(path: String) {
    add("implementation", project(path))
}

private fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

private fun DependencyHandler.project(path: String) =
    project(mapOf(Pair("path", path))) as ProjectDependency
