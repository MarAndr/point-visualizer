plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":core:loading"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
}