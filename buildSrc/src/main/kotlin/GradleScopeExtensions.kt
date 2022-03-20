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
    isPresentation: Boolean = false,
    isDFM: Boolean = false,
    testNeeded: Boolean = false,
) {
    if (!isPresentation && !isDFM) {
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
    if (testNeeded) {
        id("de.mannodermaus.android-junit5")
    }
    // id("scabbard.gradle") version Versions.Util.Scabbard
}

// dependencies
fun DependencyHandler.installSharedComposeOrbitHiltTest(
    isSharedModule: Boolean = false,
    excludeHilt: Boolean = false,
    excludeCompose: Boolean = false,
    testNeeded: Boolean = false,
) {
    implementation(Dependencies.Orbit.Main)
    if (!isSharedModule) {
        implementationProject(ProjectConstants.SharedAndroid)
    }
    if (!excludeHilt) {
        implementation(Dependencies.Jetpack.Hilt)
        add("kapt", Dependencies.Compiler.Hilt)
    }
    if (!excludeCompose) {
        Dependencies.Compose.forEach(::implementation)
        Dependencies.Debug.Compose.forEach(::debugImplementation)
        implementationProject(ProjectConstants.SharedCompose)
    }
    if (testNeeded) {
        add("testImplementation", Dependencies.Test.JunitApi)
        add("testRuntimeOnly", Dependencies.Test.JunitEngine)
        add("testImplementation", Dependencies.Test.Hamcrest)
        add("testImplementation", Dependencies.Test.Coroutine)
        // add("testDebugImplementation", Dependencies.Orbit.Test) (https://github.com/applemango-runnerbe/RunnerBe-Android/issues/84)
    }
}

fun DependencyHandler.implementationProject(path: String) {
    add("implementation", project(path))
}

private fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

private fun DependencyHandler.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

private fun DependencyHandler.project(path: String) =
    project(mapOf(Pair("path", path))) as ProjectDependency
