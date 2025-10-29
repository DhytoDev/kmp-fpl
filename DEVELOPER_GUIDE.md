# FPL App Developer Guide

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Project Structure](#project-structure)
4. [Setup and Installation](#setup-and-installation)
5. [Development Guidelines](#development-guidelines)
6. [Key Technologies](#key-technologies)
7. [Feature Implementation Guide](#feature-implementation-guide)
8. [Testing](#testing)
9. [Build and Deployment](#build-and-deployment)
10. [Troubleshooting](#troubleshooting)

## Project Overview

The FPL (Fantasy Premier League) App is a cross-platform mobile application built using Kotlin
Multiplatform (KMP) and Compose Multiplatform. It allows users to manage their Fantasy Premier
League teams, view player statistics, fixtures, and dream team selections.

### Supported Platforms

- **Android** (API 28+)
- **iOS** (iOS 16+)
- **JVM/Desktop** (Development and testing)

### Key Features

- User authentication with FPL official API
- View and manage fantasy team
- Dream team visualization
- Player statistics and summaries
- Fixture information
- Real-time data synchronization

## Architecture

The app follows **Clean Architecture** principles with clear separation of concerns:

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

### Architecture Patterns

- **MVVM** for presentation layer
- **Repository Pattern** for data management
- **Use Cases/Interactors** for business logic
- **Dependency Injection** using Koin
- **State Management** with StateFlow and UiState sealed class

## Project Structure

```
fpl-app/
├── shared/                           # Kotlin Multiplatform module
│   ├── src/
│   │   ├── commonMain/kotlin/dev/dhyto/fpl/
│   │   │   ├── core/                 # Core UI components and theme
│   │   │   │   ├── components/       # Reusable UI components
│   │   │   │   └── theme/           # App theme and colors
│   │   │   ├── data/                # Data layer
│   │   │   │   ├── data_source/     # Data source abstractions
│   │   │   │   ├── local/           # Local persistence
│   │   │   │   ├── remote/          # API clients and DTOs
│   │   │   │   └── repositories/    # Repository implementations
│   │   │   ├── domain/              # Domain layer
│   │   │   │   ├── entities/        # Domain models
│   │   │   │   ├── repositories/    # Repository interfaces
│   │   │   │   ├── usecases/        # Business logic
│   │   │   │   └── base/            # Base domain classes
│   │   │   ├── presentation/        # Presentation layer
│   │   │   │   ├── navigation/      # Navigation setup
│   │   │   │   ├── home/            # Home feature
│   │   │   │   ├── login/           # Authentication
│   │   │   │   ├── team/            # Team management
│   │   │   │   ├── dreamTeam/       # Dream team display
│   │   │   │   ├── fixtures/        # Fixtures view
│   │   │   │   └── summary/         # Manager summary
│   │   │   ├── di/                  # Dependency injection
│   │   │   └── utils/               # Utility classes
│   │   ├── androidMain/             # Android-specific code
│   │   ├── iosMain/                 # iOS-specific code
│   │   ├── jvmMain/                 # JVM-specific code
│   │   └── commonTest/              # Shared tests
│   └── sqldelight/                  # Database schemas
├── androidApp/                      # Android application
│   └── src/androidMain/
│       ├── kotlin/dev/dhyto/fpl/
│       │   ├── MainActivity.kt      # Main Android activity
│       │   └── FplApplication.kt    # Application class
│       └── AndroidManifest.xml
├── iosApp/                          # iOS application
│   └── iosApp/
│       ├── ContentView.swift        # Main iOS view
│       └── Info.plist
├── gradle/                          # Gradle configuration
├── build.gradle.kts                 # Root build file
├── settings.gradle.kts              # Gradle settings
└── README.md
```

## Setup and Installation

### Prerequisites

- **JDK 21** or higher
- **Android Studio Koala+** with Kotlin 2.2.20
- **Xcode 16+** (for iOS development)
- **Android SDK** with API level 36

### Development Environment Setup

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd fpl-app
   ```

2. **Setup Local Properties**
   Create `local.properties` file in the root directory:
   ```properties
   sdk.dir=/path/to/your/android/sdk
   ```

3. **Gradle Sync**
   ```bash
   ./gradlew build
   ```

### Running the Application

#### Android

```bash
# Build debug APK
./gradlew :androidApp:assembleDebug

# Install on device/emulator
./gradlew :androidApp:installDebug

# Run from Android Studio
# Open project → Select androidApp → Run
```

#### iOS

```bash
# Build shared frameworks
./gradlew :shared:assemble

# Open in Xcode
open iosApp/iosApp.xcworkspace
# Then build and run from Xcode
```

#### Desktop (JVM)

```bash
./gradlew :shared:run
```

## Development Guidelines

### Coding Conventions

#### Naming Conventions

- **Packages**: lowercase, dot-separated (`dev.dhyto.fpl.presentation.team`)
- **Classes/Objects/Interfaces**: PascalCase (`FplRepository`, `PlayerSummaryDto`)
- **Functions/Properties**: camelCase (`createHttpClient`, `selectPlayerToSubstitute`)
- **Constants**: UPPER_SNAKE_CASE (`BASE_PHOTO_URL`, `FAILURE_NETWORK`)
- **Composables**: PascalCase (`App`, `SignInScreen`, `PlayerView`)

#### File Organization

- One primary class per file
- File name should match the primary class name
- Related small classes can be grouped in the same file
- Use appropriate package structure following the feature-first approach

#### State Management

```kotlin
// ViewModel pattern
class MyTeamViewModel(
    private val saveOrGetMyTeam: SaveOrGetMyTeam
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ManagerEntry>>>(UiState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun loadTeam() {
        viewModelScope.launch {
            _uiState.value = UiState.LoadingState
            saveOrGetMyTeam().fold(
                ifLeft = { failure ->
                    _uiState.value = UiState.ErrorState(failure)
                },
                ifRight = { team ->
                    _uiState.value = UiState.SuccessState(team)
                }
            )
        }
    }
}
```

#### Composable Guidelines

```kotlin
@Composable
fun PlayerCard(
    player: Player,
    modifier: Modifier = Modifier,
    onPlayerClick: (Player) -> Unit = {}
) {
    // Composable implementation
    // Always provide default Modifier parameter
    // Use descriptive parameter names
    // Keep composables stateless when possible
}
```

#### Error Handling

```kotlin
// Use Arrow's Either for error handling
suspend fun getPlayers(): Either<Failure, List<Player>> {
    return try {
        val players = api.fetchPlayers()
        players.right()
    } catch (e: Exception) {
        NetworkFailure(e.message).left()
    }
}
```

### Architecture Guidelines

#### Dependency Flow

- **Presentation** → **Domain** ← **Data**
- Never let domain depend on presentation or data
- Use dependency inversion principle
- Inject dependencies through constructors

#### Data Flow

1. **UI** triggers action
2. **ViewModel** calls **Use Case**
3. **Use Case** calls **Repository**
4. **Repository** coordinates between **Remote** and **Local** data sources
5. Data flows back through the same path

## Key Technologies

### Core Technologies

- **Kotlin Multiplatform 2.2.20**: Shared business logic
- **Compose Multiplatform 1.9.0**: Shared UI framework
- **Koin 3.5.1**: Dependency injection
- **Ktor 3.3.0**: HTTP client for API calls
- **SQLDelight 2.0.0**: Type-safe SQL database
- **PreCompose 1.6.0**: Navigation and lifecycle
- **Arrow 2.1.2**: Functional programming utilities
- **Kotlinx Serialization 1.9.0**: JSON serialization

### Platform-Specific

- **Android**: Activity, Android SDK 28+
- **iOS**: SwiftUI wrapper, iOS 16+
- **JVM**: Desktop support for development

### Development Tools

- **Kermit**: Multiplatform logging
- **Mockative**: Mocking for tests
- **Kamel**: Image loading
- **Multiplatform Settings**: Shared preferences

## Feature Implementation Guide

### Adding a New Feature

1. **Create Domain Entities**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/domain/entities/
   data class NewFeature(
       val id: Int,
       val name: String,
       // other properties
   )
   ```

2. **Define Repository Interface**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/domain/repositories/
   interface INewFeatureRepository {
       suspend fun getNewFeature(): Either<Failure, NewFeature>
   }
   ```

3. **Create Use Case**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/domain/usecases/
   class GetNewFeature(
       private val repository: INewFeatureRepository
   ) {
       suspend operator fun invoke(): Either<Failure, NewFeature> {
           return repository.getNewFeature()
       }
   }
   ```

4. **Implement API Client**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/data/remote/
   suspend fun fetchNewFeature() = 
       client.get("new-feature/").body<NewFeatureDto>()
   ```

5. **Create Repository Implementation**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/data/repositories/
   class NewFeatureRepository(
       private val api: FantasyPremierLeagueApi
   ) : INewFeatureRepository {
       override suspend fun getNewFeature(): Either<Failure, NewFeature> {
           return try {
               val dto = api.fetchNewFeature()
               dto.toDomain().right()
           } catch (e: Exception) {
               NetworkFailure(e.message).left()
           }
       }
   }
   ```

6. **Create ViewModel**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/presentation/newfeature/
   class NewFeatureViewModel(
       private val getNewFeature: GetNewFeature
   ) : ViewModel() {
       private val _uiState = MutableStateFlow<UiState<NewFeature>>(UiState.InitialState)
       val uiState = _uiState.asStateFlow()
       
       fun loadFeature() {
           viewModelScope.launch {
               _uiState.value = UiState.LoadingState
               getNewFeature().fold(
                   ifLeft = { _uiState.value = UiState.ErrorState(it) },
                   ifRight = { _uiState.value = UiState.SuccessState(it) }
               )
           }
       }
   }
   ```

7. **Create Composable Screen**
   ```kotlin
   @Composable
   fun NewFeatureScreen(
       viewModel: NewFeatureViewModel = koinViewModel()
   ) {
       val uiState by viewModel.uiState.collectAsStateWithLifecycle()
       
       LaunchedEffect(Unit) {
           viewModel.loadFeature()
       }
       
       when (uiState) {
           is UiState.LoadingState -> CircularProgressIndicator()
           is UiState.SuccessState -> NewFeatureContent(uiState.data)
           is UiState.ErrorState -> ErrorDisplay(uiState.failure)
           is UiState.InitialState -> Unit
       }
   }
   ```

8. **Register Dependencies**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/di/SharedModule.kt
   single<INewFeatureRepository> { NewFeatureRepository(get()) }
   factory { GetNewFeature(get()) }
   factory { NewFeatureViewModel(get()) }
   ```

9. **Add Navigation**
   ```kotlin
   // shared/src/commonMain/kotlin/dev/dhyto/fpl/presentation/navigation/
   object NewFeatureRoute : NavigationRoute("/new-feature")
   
   // In Navigation.kt
   scene(route = NewFeatureRoute.route) {
       NewFeatureScreen()
   }
   ```

## Testing

### Test Structure

- **commonTest**: Shared test code
- **androidUnitTest**: Android-specific unit tests
- **iosTest**: iOS-specific tests

### Testing Libraries

- **Kotlin Test**: Multiplatform testing framework
- **Mockative**: Mocking library with KSP
- **Turbine**: Flow testing utilities
- **Kotlinx Coroutines Test**: Coroutine testing

### Example Test

```kotlin
class FplRepositoryTest {
    @Mock
    private val dataSource = mock<IFplDataSource>()

    private val repository = FplRepository(dataSource)

    @Test
    fun `getDreamTeamSquad returns success when data source succeeds`() = runTest {
        // Given
        val expectedPlayers = listOf(/* test data */)
        every { dataSource.fetchDreamTeam(any()) } returns expectedPlayers.right()

        // When
        val result = repository.getDreamTeamSquad(1)

        // Then
        result.shouldBeRight(expectedPlayers)
    }
}
```

## Build and Deployment

### Build Configurations

- **Debug**: Development builds with logging enabled
- **Release**: Production builds with optimizations

### Android Deployment

```bash
# Generate signed APK
./gradlew :androidApp:assembleRelease

# Generate AAB for Play Store
./gradlew :androidApp:bundleRelease
```

### iOS Deployment

1. Build shared framework: `./gradlew :shared:assemble`
2. Open Xcode workspace
3. Archive and distribute through Xcode

### Environment Variables

- `enableNetworkLogs`: Enable/disable network logging
- Various API endpoints can be configured in `NetworkModule.kt`

## Troubleshooting

### Common Issues

#### Gradle Issues

**Problem**: Gradle sync fails
**Solution**:

```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

#### iOS Build Issues

**Problem**: iOS framework not found
**Solution**:

```bash
./gradlew :shared:clean
./gradlew :shared:assemble
```

#### SQLDelight Issues

**Problem**: Database queries not generated
**Solution**: Clean and rebuild the project

#### Network Issues

**Problem**: API calls failing
**Solution**: Check network configuration in `NetworkModule.kt` and ensure proper authentication

### Performance Tips

- Use `LazyColumn` for large lists
- Implement proper image caching with Kamel
- Use `remember` for expensive calculations in Composables
- Optimize database queries in SQLDelight

### Debugging

- Use Kermit logging for cross-platform debugging
- Enable network logs in debug builds
- Use Compose Inspector for UI debugging
- Leverage platform-specific debugging tools (Android Studio Debugger, Xcode Debugger)

## Contributing

1. Follow the established coding conventions
2. Write tests for new features
3. Update documentation for significant changes
4. Use meaningful commit messages
5. Create feature branches for new work
6. Submit pull requests for review

## Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Fantasy Premier League API](https://fantasy.premierleague.com/api/)
- [Engineering Guidelines](./.junie/guidelines.md)

---

For questions or support, please refer to the existing codebase examples or create an issue in the
project repository.