plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":features:points:points-api"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
}