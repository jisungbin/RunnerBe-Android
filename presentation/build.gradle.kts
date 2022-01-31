plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
}

android {
    defaultConfig {
        versionCode = ApplicationConstants.versionCode
        versionName = ApplicationConstants.versionName
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.shared)
    implementation(Dependencies.Hilt)
    implementation(Dependencies.Coroutine)
    implementation(Dependencies.Firebase.Analytics)
    implementation(platform(Dependencies.Firebase.Bom))

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Ktx.forEach(::implementation)
    Dependencies.Util.forEach(::implementation)
    Dependencies.Compose.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)

    Dependencies.Debug.forEach(::debugImplementation)

    kapt(Dependencies.Compiler.Hilt)
}
