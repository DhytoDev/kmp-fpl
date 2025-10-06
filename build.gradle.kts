plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
//    kotlin("native.cocoapods") version "2.0.0"
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.sqlDelight) apply false
}


allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            // allWarningsAsErrors.set(true)
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
            )
        }
    }
}