plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    jvmToolchain(11)

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // put your Multiplatform dependencies here
                implementation(project.dependencies.platform(libs.koin.bom))

                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)

                api(libs.koin.core)
                api(libs.koin.core.coroutines)
                api(libs.koin.compose)
                api(libs.bundles.precompose)

                implementation(libs.bundles.ktor.common)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)


                implementation(libs.kotlinx.datetime)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)


            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.java)
//            implementation(libs.slf4j)
            }
        }
    }
}

android {
    namespace = "dev.dhyto.fpl.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
