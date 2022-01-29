import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.Essential.Gradle}")
        classpath("com.google.gms:google-services:${Versions.Essential.GoogleService}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Jetpack.Hilt}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}")
        classpath("com.google.android.gms:oss-licenses-plugin:${Versions.OssLicense.Classpath}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xopt-in=kotlin.OptIn"
                )
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
