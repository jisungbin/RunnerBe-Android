/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 4:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        // maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath("com.google.gms:google-services:${Versions.Essential.GoogleService}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Jetpack.Hilt}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:${Versions.Test.JUnitGradle}")
        classpath("com.google.android.gms:oss-licenses-plugin:${Versions.OssLicense.Classpath}")
        classpath("com.vanniktech:gradle-dependency-graph-generator-plugin:${Versions.Util.DependencyGraphGenerator}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        project.apply("$rootDir/gradle/common.gradle")
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.OptIn",
                    "-Xopt-in=kotlin.RequiresOptIn"
                )
            }
        }
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.github.kittinunf.result" && requested.name == "result" && requested.version == "3.0.0") {
                useVersion("3.0.1")
                because("Transitive dependency of Scabbard, currently not available on mavenCentral()")
            }
        }
    }
}

subprojects {
    // https://github.com/gradle/gradle/issues/4823#issuecomment-715615422
    @Suppress("UnstableApiUsage")
    if (gradle.startParameter.isConfigureOnDemand &&
        buildscript.sourceFile?.extension?.toLowerCase() == "kts" &&
        parent != rootProject
    ) {
        generateSequence(parent) { project -> project.parent.takeIf { it != rootProject } }
            .forEach { evaluationDependsOn(it.path) }
    }
}

tasks.register("clean", Delete::class) {
    allprojects.map { it.buildDir }.forEach(::delete)
}

apply {
    plugin("com.vanniktech.dependency.graph.generator")
    from("gradle/projectDependencyGraph.gradle")
}
