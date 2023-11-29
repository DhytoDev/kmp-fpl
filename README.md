This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.

* `/androidApp` This is a Kotlin module that builds into an Android application. 
   It uses Gradle as the build system. The androidApp module depends on and uses the shared module as a regular Android library.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…