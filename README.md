This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

- /shared is for the code that will be shared between all targets in the project. The most important
  subfolder is commonMain. If preferred, you can add code to the platform-specific folders here too.
- /androidApp This is a Kotlin module that builds into an Android application. It uses Gradle as the
  build system. The androidApp module depends on and uses the shared module as a regular Android
  library.
- /iosApp contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, you
  need this entry point for your iOS app. This is also where you should add SwiftUI code for your
  project.

How to build and run

Android

- Requirements: Android Studio Koala+ or AGP-compatible, JDK 21, Android SDK Platform matching
  compileSdk 36.
- Build from CLI: ./gradlew :androidApp:assembleDebug
- Install to a device/emulator: ./gradlew :androidApp:installDebug
- Run from Android Studio: Open the project, select androidApp, choose a device/emulator, and click
  Run.

iOS

- Requirements: Xcode 16+, Cocoapods not required. Kotlin 2.2.20 toolchain already configured via
  Gradle.
- Build shared frameworks via Gradle: ./gradlew :shared:assemble
- Open iosApp/iosApp.xcworkspace in Xcode and run on a simulator/device.

Notes

- We excluded Compose UI test artifacts from the app runtime to prevent duplicate classes during
  Android builds.
- If you previously had a corrupted Gradle cache, you can use
  scripts/repro_gradle_kotlin_dsl_cache_issue.sh to reproduce issues with a clean Gradle user home.

Learn more about Kotlin
Multiplatform: https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html