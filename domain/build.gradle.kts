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

dependencies {
    // Nothing. 100% DOMAIN!
}
