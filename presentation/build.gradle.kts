plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
        }
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Master
    }
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(Dependencies.Hilt)
    implementation(Dependencies.Coroutine)

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Ktx.forEach(::implementation)
    Dependencies.Util.forEach(::implementation)
    Dependencies.Compose.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    Dependencies.Debug.forEach(::debugImplementation)

    kapt(Dependencies.Compiler.Hilt)
}
