plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        multiDexEnabled = true
    }

    sourceSets {
        getByName("main").run {
            java.srcDirs("src/main/kotlin")
        }
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    implementation(projects.domain)
    implementation(platform(Dependencies.Firebase.Bom))
    implementation(Dependencies.Coroutine)
    implementation(Dependencies.Firebase.Analytics)

    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)
}
