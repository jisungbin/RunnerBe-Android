plugins {
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
    installLibraryKotlinKaptHiltJUnit5(isLibrary = false)
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
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.Firebase.Analytics)
    implementation(platform(Dependencies.Firebase.Bom))

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Debug.forEach(::debugImplementation)

    installSharedHiltJUnit5()
}
