# FPL App Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites Checklist

- [ ] JDK 21+ installed
- [ ] Android Studio Koala+ with Kotlin 2.2.20
- [ ] Xcode 16+ (for iOS development)
- [ ] Android SDK API 36

### Setup Commands

```bash
# 1. Clone and navigate
git clone <repository-url>
cd fpl-app

# 2. Build the project
./gradlew build

# 3. Run Android
./gradlew :androidApp:installDebug

# 4. Run iOS (after Android setup)
./gradlew :shared:assemble
open iosApp/iosApp.xcworkspace
```

## 📱 Project Structure at a Glance

```
fpl-app/
├── shared/           # 🔄 Cross-platform code (Kotlin Multiplatform)
│   ├── data/         # 🌐 API, Database, Repositories
│   ├── domain/       # 🎯 Business Logic, Entities, Use Cases
│   ├── presentation/ # 🖼️ UI Screens, ViewModels
│   └── di/           # 💉 Dependency Injection
├── androidApp/       # 🤖 Android-specific code
└── iosApp/          # 🍎 iOS-specific code
```

## 🛠️ Core Technologies

| Technology            | Purpose                | Version |
|-----------------------|------------------------|---------|
| Kotlin Multiplatform  | Shared business logic  | 2.2.20  |
| Compose Multiplatform | Shared UI              | 1.9.0   |
| Ktor                  | HTTP client            | 3.3.0   |
| SQLDelight            | Type-safe database     | 2.0.0   |
| Koin                  | Dependency injection   | 3.5.1   |
| Arrow                 | Functional programming | 2.1.2   |

## 🏗️ Architecture Overview

```
UI (Compose) → ViewModel → Use Case → Repository → Data Source (API/DB)
```

### Data Flow Example

```kotlin
// 1. UI triggers action
Button(onClick = { viewModel.loadTeam() })

// 2. ViewModel calls Use Case
viewModel.loadTeam() → saveOrGetMyTeam()

// 3. Use Case calls Repository
saveOrGetMyTeam() → repository.getMyTeam()

// 4. Repository fetches data
repository.getMyTeam() → api.fetchMyTeam()+database.getTeam()
```

## 📋 Common Development Tasks

### Adding a New Screen

1. Create entity in `domain/entities/`
2. Add repository interface in `domain/repositories/`
3. Create use case in `domain/usecases/`
4. Implement repository in `data/repositories/`
5. Create ViewModel in `presentation/feature/`
6. Build Composable screen
7. Register dependencies in `di/SharedModule.kt`
8. Add navigation route

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific platform tests
./gradlew :shared:testDebugUnitTest  # Android
./gradlew :shared:iosSimulatorArm64Test  # iOS
```

### Debugging Network Issues

```kotlin
// Enable network logs in debug
val enableNetworkLogs = BuildConfig.DEBUG

// Check logs with tag "KtorClient"
Logger.d(tag = "KtorClient") { "API Response: $response" }
```

## 🎨 UI Development Tips

### State Management Pattern

```kotlin
class FeatureViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Data>>(UiState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.LoadingState
            useCase().fold(
                ifLeft = { _uiState.value = UiState.ErrorState(it) },
                ifRight = { _uiState.value = UiState.SuccessState(it) }
            )
        }
    }
}
```

### Composable Best Practices

```kotlin
@Composable
fun FeatureScreen(
    modifier: Modifier = Modifier, // Always provide default Modifier
    viewModel: FeatureViewModel = koinViewModel() // Use Koin for DI
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadData() // Trigger initial load
    }

    when (uiState) {
        is UiState.LoadingState -> LoadingIndicator()
        is UiState.SuccessState -> Content(uiState.data)
        is UiState.ErrorState -> ErrorMessage(uiState.failure)
        UiState.InitialState -> Unit
    }
}
```

## 🔧 Troubleshooting Quick Fixes

| Problem                | Solution                                                    |
|------------------------|-------------------------------------------------------------|
| Gradle sync fails      | `./gradlew clean && ./gradlew build --refresh-dependencies` |
| iOS build fails        | `./gradlew :shared:clean && ./gradlew :shared:assemble`     |
| Database issues        | Clean and rebuild project                                   |
| Network timeouts       | Check `NetworkModule.kt` configuration                      |
| Compose preview broken | Invalidate caches and restart                               |

## 📚 Key Files to Know

| File                                                                                | Purpose                     |
|-------------------------------------------------------------------------------------|-----------------------------|
| `shared/src/commonMain/kotlin/dev/dhyto/fpl/presentation/App.kt`                    | Main app entry point        |
| `shared/src/commonMain/kotlin/dev/dhyto/fpl/di/SharedModule.kt`                     | Dependency injection setup  |
| `shared/src/commonMain/kotlin/dev/dhyto/fpl/data/remote/FantasyPremierLeagueApi.kt` | API client                  |
| `shared/src/commonMain/kotlin/dev/dhyto/fpl/presentation/UiState.kt`                | UI state management         |
| `shared/build.gradle.kts`                                                           | Shared module configuration |

## 🎯 Development Workflow

1. **Feature Branch**: Create branch from `main`
2. **Domain First**: Start with domain entities and use cases
3. **Data Layer**: Implement repository and API calls
4. **UI Layer**: Create ViewModel and Composable
5. **Testing**: Write unit tests for business logic
6. **Integration**: Register dependencies and test end-to-end
7. **Review**: Submit PR with meaningful description

## 🔍 Code Examples

### API Call with Error Handling

```kotlin
suspend fun fetchPlayers(): Either<Failure, List<Player>> {
    return try {
        val response = api.fetchBootstrapStaticInfo()
        response.elements.map { it.toDomain() }.right()
    } catch (e: HttpException) {
        NetworkFailure(e.message).left()
    }
}
```

### Database Operation

```kotlin
// Insert player
database.playerQueries.insertPlayer(
    id = player.id,
    fullName = player.name,
    displayName = player.displayName,
    // ... other fields
)

// Query players
val players = database.playerQueries.getAllPlayers().executeAsList()
```

### Navigation

```kotlin
// Define route
object PlayerDetailRoute : NavigationRoute("/player/{playerId}")

// Navigate to route
navigator.navigate("${PlayerDetailRoute.route}/${playerId}")

// Handle in Navigation.kt
scene(route = PlayerDetailRoute.route) { backStackEntry ->
    val playerId = backStackEntry.path<Int>("playerId")
    PlayerDetailScreen(playerId = playerId)
}
```

## 📞 Getting Help

- Check [DEVELOPER_GUIDE.md](./DEVELOPER_GUIDE.md) for detailed documentation
- Review [Engineering Guidelines](./.junie/guidelines.md) for conventions
- Look at existing code examples in the codebase
- Create GitHub issues for bugs or feature requests

---

**Happy Coding!** 🎉 For more detailed information, see the
complete [Developer Guide](./DEVELOPER_GUIDE.md).