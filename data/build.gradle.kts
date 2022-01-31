plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android { }

/*android {
    compileSdk = ApplicationConstants.compileSdk

    defaultConfig {
        minSdk = ApplicationConstants.minSdk
        targetSdk = ApplicationConstants.targetSdk
        multiDexEnabled = true
    }

    sourceSets {
        getByName("main").run {
            java.srcDirs("src/main/kotlin")
        }
    }

    compileOptions {
        sourceCompatibility = ApplicationConstants.sourceCompat
        targetCompatibility = ApplicationConstants.targetCompat
    }

    kotlinOptions {
        jvmTarget = ApplicationConstants.jvmTarget
    }
}*/

/*
dependencies {
    implementation(projects.domain)
    implementation(Dependencies.Coroutine)

    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)
}
*/
