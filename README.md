This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

## 📚 Documentation

### For New Developers

- **[Quick Start Guide](./QUICK_START.md)** - Get up and running in 5 minutes
- **[Developer Guide](./DEVELOPER_GUIDE.md)** - Comprehensive development documentation
- **[API Reference](./API_REFERENCE.md)** - Fantasy Premier League API integration details
- **[Engineering Guidelines](./.junie/guidelines.md)** - Coding conventions and project structure

## 🏗️ Project Structure

- `/shared` is for the code that will be shared between all targets in the project. The most
  important
  subfolder is commonMain. If preferred, you can add code to the platform-specific folders here too.
- `/androidApp` This is a Kotlin module that builds into an Android application. It uses Gradle as
  the
  build system. The androidApp module depends on and uses the shared module as a regular Android
  library.
- `/iosApp` contains iOS applications. Even if you're sharing your UI with Compose Multiplatform,
  you
  need this entry point for your iOS app. This is also where you should add SwiftUI code for your
  project.

## 🚀 How to build and run

### Android

- Requirements: Android Studio Koala+ or AGP-compatible, JDK 21, Android SDK Platform matching
  compileSdk 36.
- Build from CLI: `./gradlew :androidApp:assembleDebug`
- Install to a device/emulator: `./gradlew :androidApp:installDebug`
- Run from Android Studio: Open the project, select androidApp, choose a device/emulator, and click
  Run.

### iOS

- Requirements: Xcode 16+, Cocoapods not required. Kotlin 2.2.20 toolchain already configured via
  Gradle.
- Build shared frameworks via Gradle: `./gradlew :shared:assemble`
- Open iosApp/iosApp.xcworkspace in Xcode and run on a simulator/device.

## 🛠️ Tech Stack

### Core Technologies

- **Kotlin Multiplatform 2.2.20** - Shared business logic
- **Compose Multiplatform 1.9.0** - Cross-platform UI
- **Ktor 3.3.0** - HTTP client for API calls
- **SQLDelight 2.0.0** - Type-safe database
- **Koin 3.5.1** - Dependency injection
- **Arrow 2.1.2** - Functional programming utilities

### Architecture

- **Clean Architecture** with clear layer separation
- **MVVM** pattern for presentation layer
- **Repository Pattern** for data management
- **Use Cases** for business logic encapsulation

## 📱 Features

- 🔐 **Authentication** - Secure login with FPL official API
- 👥 **Team Management** - View and modify your fantasy team
- ⭐ **Dream Team** - View official weekly dream team selections
- 📊 **Player Stats** - Detailed player information and statistics
- 🏟️ **Fixtures** - Upcoming match information
- 💾 **Offline Support** - Cached data for offline viewing

## 🏛️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Compose UI │  │ ViewModels  │  │  Navigation         │  │
│  │  Screens    │  │             │  │  (PreCompose)       │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                               │
┌─────────────────────────────────────────────────────────────┐
│                     Domain Layer                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Entities   │  │ Use Cases   │  │  Repository         │  │
│  │             │  │             │  │  Interfaces         │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                               │
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ Remote API  │  │ Local DB    │  │  Repository         │  │
│  │ (Ktor)      │  │ (SQLDelight)│  │  Implementations    │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## 🔧 Development

### Quick Commands

```bash
# Build project
./gradlew build

# Run tests
./gradlew test

# Android development
./gradlew :androidApp:installDebug

# iOS development  
./gradlew :shared:assemble
open iosApp/iosApp.xcworkspace
```

### Adding a New Feature

1. Create domain entities and use cases
2. Implement repository interfaces
3. Build data layer (API + local storage)
4. Create ViewModels and UI screens
5. Register dependencies in Koin modules
6. Add navigation routes

See the [Developer Guide](./DEVELOPER_GUIDE.md) for detailed instructions.

## 🧪 Testing

- **Unit Tests**: `./gradlew test`
- **Android Tests**: `./gradlew :shared:testDebugUnitTest`
- **iOS Tests**: `./gradlew :shared:iosSimulatorArm64Test`

Testing stack includes Kotlin Test, Mockative for mocking, and Turbine for Flow testing.

## 📝 Notes

- We excluded Compose UI test artifacts from the app runtime to prevent duplicate classes during
  Android builds.
- If you previously had a corrupted Gradle cache, you can use
  scripts/repro_gradle_kotlin_dsl_cache_issue.sh to reproduce issues with a clean Gradle user home.

## 📖 Learn more

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Fantasy Premier League API](https://fantasy.premierleague.com/api/)

## 🤝 Contributing

1. Read the [Developer Guide](./DEVELOPER_GUIDE.md)
   and [Engineering Guidelines](./.junie/guidelines.md)
2. Follow the established coding conventions
3. Write tests for new features
4. Submit pull requests with clear descriptions

---

**Get started with the [Quick Start Guide](./QUICK_START.md)** 🚀