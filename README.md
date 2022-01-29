# AndroidBaseComposeTemplate

Android base Jetpack Compose project template with Gradle Kotlin-DSL and Clean Architecture.

# Includes

```kotlin
val essential = listOf(
    "androidx.core:core-ktx:${Versions.Essential.Ktx}",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
)

val compose = listOf(
    "androidx.compose.ui:ui:${Versions.Compose.Master}",
    "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
    "androidx.compose.compiler:compiler:${Versions.Compose.Master}",
    "androidx.compose.material:material:${Versions.Compose.Master}",
    "androidx.activity:activity-compose:${Versions.Compose.Activity}",
    "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Master}",
    "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.Lifecycle}"
)

val ui = listOf(
    "com.google.android.material:material:${Versions.Ui.Material}"
)
```
