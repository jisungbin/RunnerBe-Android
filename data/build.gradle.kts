plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

dependencies {
    implementation(projects.domain)
    implementation(Dependencies.Coroutine)

    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Network.forEach(::implementation)
}
