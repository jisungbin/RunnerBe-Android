android {
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
        sourceCompatibility = ApplicationConstants.javaVersion
        targetCompatibility = ApplicationConstants.javaVersion
    }

    kotlinOptions {
        jvmTarget = ApplicationConstants.jvmTarget
    }
}
