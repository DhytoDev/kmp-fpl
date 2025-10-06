plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(project(":shared"))
                implementation(libs.androidx.activity.compose)
                api(libs.koin.android)
            }
        }
    }
}

// Exclude Compose UI test artifacts from app runtime to avoid duplicate classes
configurations.configureEach {
    exclude(group = "androidx.compose.ui", module = "ui-test-junit4-android")
    exclude(group = "androidx.compose.ui", module = "ui-test-android")
}

android {
    namespace = "dev.dhyto.fpl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.dhyto.fpl"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
//            excludes += "/META-INF/*"
//            excludes += "**/*"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/**"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
