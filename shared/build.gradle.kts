plugins {
//    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            compilation.apply {
                kotlinOptions.freeCompilerArgs += arrayOf("-linker-options", "-lsqlite3")
            }
        }
    }

    jvm()

    jvmToolchain(17)

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlin.experimental.ExperimentalObjCName")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                optIn("org.jetbrains.compose.resources.InternalResourceApi")
            }

        }

        val commonMain by getting {

            dependencies {
                // put your Multiplatform dependencies here
                implementation(project.dependencies.platform(libs.koin.bom))

                implementation(libs.kamel.image)

                implementation(libs.arrow.core)
                implementation(libs.arrow.fx.coroutines)

                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.datetime)

                api(libs.koin.core)
                api(libs.koin.core.coroutines)
                api(libs.koin.compose)
                api(libs.bundles.precompose)

                implementation(libs.bundles.ktor.common)

                implementation(libs.ktor.client.mock)
                implementation(libs.logging.kermit)

                implementation(libs.multiplatform.settings)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
//                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
//                implementation(compose.components.uiToolingPreview)
            }
        }

        val androidMain by getting {
            dependencies {
//                dependsOn(commonMain)
                implementation(libs.ktor.client.android)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.sqlDelight.android.driver)
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
                implementation(libs.sqlDelight.native.driver)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.java)
                implementation(libs.sqlDelight.jvm.driver)
                implementation(compose.preview)
//            implementation(libs.slf4j)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.mockative)
                implementation(libs.bundles.shared.common.test)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlin("test"))
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation(libs.sqlDelight.jvm.driver)
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

sqldelight {
    databases {
        create("FPLDatabase") {
            packageName.set("dev.dhyto.fpl")
//            srcDirs("/databases", "/migrations")
        }
    }
}

android {
    namespace = "dev.dhyto.fpl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:2.0.1")
        }
}
