plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.pointvisualizer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pointvisualizer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

kapt {
   correctErrorTypes = true
}

dependencies {
    implementation(project(":core:loading"))
    implementation(project(":core:network"))

    implementation(project(":features:points:points-api"))
    implementation(project(":features:points:points-impl"))
    implementation(project(":features:files:files-api"))
    implementation(project(":features:files:files-impl"))

    implementation(project(":ui:core"))
    implementation(project(":ui:navigation:navigation-api"))
    implementation(project(":ui:navigation:navigation-impl"))
    implementation(project(":ui:enterpoints"))
    implementation(project(":ui:graph"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}