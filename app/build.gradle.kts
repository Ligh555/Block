plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ligh.blog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ligh.blog"
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        aidl = true
    }
}

dependencies {
    implementation(project(":android"))
    implementation(project(":base"))
    implementation(project(":ipc"))
    implementation(project(":biometric"))
    implementation(libs.bundles.navigation.ktx)
    implementation(libs.bundles.andorid.base)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hilt.compiler)

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")


}