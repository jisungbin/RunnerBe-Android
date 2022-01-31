plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
    installKaptHiltJUnit5()
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
    /*val features = listOf(
        projects.features.alarm,
        projects.features.board,
        projects.features.extrainformation,
        projects.features.favorite,
        projects.features.mail,
        projects.features.mypage,
        projects.features.snslogin,
        projects.features.writing,
    )

    features.forEach(::implementation)*/
    implementation(projects.shared)
    implementation(Dependencies.Firebase.Analytics)
    implementation(platform(Dependencies.Firebase.Bom))

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Debug.forEach(::debugImplementation)

    Dependencies.Test.forEach(::testImplementation)

    installHiltJUnit5()
}
