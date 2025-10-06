# Engineering Guidelines

This document summarizes the conventions and structure currently used in this repository to help maintain consistency and ease onboarding.


## Coding conventions used in the current codebase

- Language and toolchain
  - Kotlin Multiplatform (KMP) with shared business logic and UI using Compose Multiplatform.
  - Gradle Kotlin DSL (build.gradle.kts) for build configuration.
  - Ktor client for networking, kotlinx.serialization for JSON, SQLDelight for persistence, Koin for DI, Kermit for logging, PreCompose for navigation/lifecycle helpers.

- Packages and naming
  - Root package: `dev.dhyto.fpl`.
  - Package names are all lowercase, dot-separated, no underscores (e.g., `dev.dhyto.fpl.presentation.team`).
  - Classes, objects, interfaces, enums: PascalCase (e.g., `FplApplication`, `MyTeamViewModel`, `PlayerSummaryDto`).
  - Functions and properties: camelCase (e.g., `createHttpClient`, `selectPlayerToSubstitute`).
  - Constants: UPPER_SNAKE_CASE, usually inside companion objects or dedicated objects (e.g., `BASE_PHOTO_URL`, `FailureConst.UNAUTHENTICATED_FAILURE`). Use `const val` where applicable.
  - Files generally contain a primary top-level type or a small, closely related group (e.g., `Player.kt` with domain entity and nested enum).

- Compose UI patterns
  - Composable function names use PascalCase (e.g., `App`, `RootScreen`, `MyTeamScreen`, `TeamPicksBody`).
  - `modifier: Modifier = Modifier` is provided as a parameter to most composables (typically positioned first), enabling composition and customization.
  - Stateless/UI-only composables prefer deriving state from parameters; state is generally hoisted to ViewModels.
  - Feature code is split by screen/feature package, and complex UIs are decomposed into smaller composables (e.g., `MiniPlayerProfile`, `PlayerView`).

- State management and ViewModels
  - ViewModels expose UI state via `StateFlow` of a sealed `UiState<T>` (Initial/Loading/Success/Error) defined in `presentation/UiState.kt`.
  - Internally, `MutableStateFlow` is used with `asStateFlow()` for encapsulation; `stateIn` and `SharingStarted` are used to scope flows to lifecycle.
  - In Compose, flows are observed with PreCompose helpers like `collectAsStateWithLifecycle`.

- Coroutines and threading
  - Suspend functions are used across data sources and repositories for async work.
  - Flows model ongoing state updates; collectors use lifecycle-aware APIs in UI.

- Networking and serialization
  - `HttpClient` is configured in `di/NetworkModule.kt` with `ContentNegotiation` and `kotlinx.serialization` (`Json { ignoreUnknownKeys = true, ... }`).
  - Responses are validated via `HttpResponseValidator` and custom `HttpExceptions` for consistent error handling.
  - DTOs are annotated with `@Serializable` and `@SerialName` when JSON field names differ.

- Dependency Injection (DI)
  - Koin modules live in `dev.dhyto.fpl.di`.
  - `initKoin` wires `sharedModule`, `platformModule`, and `cacheModule`. Android adds `appModule` in `androidApp`.
  - ViewModels and use cases are provided as `factory` or `single` bindings; repositories and APIs are generally `single`.

- Persistence
  - SQLDelight database is provided via DI (`cacheModule()` creates `FPLDatabase`).
  - Drivers are provided using KMP `expect/actual` pattern via `data.local.DriverFactory`.

- Multiplatform patterns
  - Platform-specific implementations use `expect/actual` and platform-suffixed files (e.g., `NetworkModule.android.kt`, `NetworkModule.ios.kt`, `DateTimeFormatter.android.kt`, etc.).
  - `createHttpClientEngine()` is `expect`ed in common and implemented with platform engines (Android, Darwin, JVM).

- Error handling
  - Domain failures are modeled with `sealed class Failure` (e.g., `NetworkFailure`, `UnauthenticatedFailure`).
  - UI state exposes errors via `UiState.ErrorState(Failure?)`.

- Logging
  - Kermit is used for network logging when enabled; logs are tagged (e.g., `KtorClient`).

- Import/formatting tendencies
  - Explicit imports are preferred; wildcard imports are not used in current code.
  - Kotlin defaults (4-space indentation, trailing commas optional) are followed; code favors immutability and null-safety.


## Code organization and package structure

Top-level modules
- `shared/` — KMP module shared by all targets.
- `androidApp/` — Android application module (entry point, Android DI wiring).
- `iosApp/` — iOS application wrapper/workspace (entry point on iOS).

Shared module (`shared/src/.../kotlin/dev/dhyto/fpl`)
- `core/`
  - `theme/` — Theme setup and colors for Compose (`Theme.kt`, `Color.kt`).
  - `components/` — Reusable UI building blocks (`ListTile`, `ShimmerEffect`, etc.).
- `data/`
  - `data_source/` — Abstractions and their implementations to orchestrate data from APIs, cache, etc. (`IFplDataSource`, `FplDataSource`).
  - `remote/` — Ktor API clients and DTO models (`FantasyPremierLeagueApi`, `FPLAuthenticationApi`, `.../model/*`).
  - `local/` — Local persistence utilities and mappers (`DriverFactory` expect/actual, `KeyValuePersistence`, `.../mapper/*`).
  - `repositories/` — Implementations of domain repository interfaces (`FplRepository`, `FplAuthRepository`, mappers like `TeamPicksMapper`).
- `domain/`
  - `entities/` — Pure domain models (`Player`, `Team`, `Fixture`, etc.).
  - `repositories/` — Domain repository interfaces (`IFplRepository`, `IFplAuthRepository`).
  - `usecases/` — Application use cases/interactors (`SaveOrGetMyTeam`, `GetDreamTeamAndFixtures`, ...).
  - `base/` — Shared domain primitives (e.g., `Failure`).
- `presentation/`
  - Root UI (`App.kt`, `UiState.kt`).
  - `navigation/` — PreCompose navigation setup (`Navigation.kt`, `NavigationRoute.kt`).
  - Feature-first subpackages such as:
    - `home/`
    - `login/` (e.g., `SignInScreen`, `SignInViewModel`)
    - `dreamTeam/` (e.g., `DreamTeamAndFixturesViewModel`)
    - `summary/` (manager info)
    - `team/` (team selection) with deeper grouping like `teamPicks/` (`TeamPicksBody`, `PlayerView`, etc.).
- `di/`
  - DI composition for shared and platform needs: `SharedModule`, `PlatformModule`, `NetworkModule` (common), and platform-specific actuals (`NetworkModule.android/ios/jvm.kt`).
  - `CacheModule` wires SQLDelight database (`FPLDatabase`).
- `utils/`
  - Cross-platform utilities using expect/actual where needed (e.g., `DateTimeFormatter` with `*.android.kt`, `*.ios.kt`, `*.jvm.kt`).
- `sqldelight/`
  - SQLDelight schema and queries backing the generated `FPLDatabase`.

Platform modules
- `androidApp/src/androidMain/kotlin/dev/dhyto/fpl`
  - `FplApplication` — Android Application class initializes Koin (adds `appModule`).
  - `MainActivity` — Activity hosting Compose `App()`.
  - `di/AppModule.kt` — Android-specific Koin module(s) if needed.
- `shared/src/androidMain|iosMain|jvmMain/kotlin/dev/dhyto/fpl`
  - Platform-specific actual implementations for DI/networking, persistence drivers, and utilities (e.g., `NetworkModule.android.kt`, `DriverFactory`, `KeyValuePersistence` platform variants).

Testing structure
- Tests are organized per source set (e.g., `shared/src/commonTest`, `shared/src/androidUnitTest`, `shared/src/iosTest`, etc.). Use appropriate platform runners and KMP testing tools.


Notes for contributors
- Favor feature-first packages in `presentation` and keep UI logic in ViewModels; composables should remain stateless when possible.
- Keep domain entities free of platform and framework concerns.
- Add new APIs/DTOs under `data/remote`, map them to domain entities in repositories or dedicated mappers.
- Register new dependencies in the appropriate Koin module(s) under `di/`.
- When adding platform-specific behavior, prefer `expect/actual` with explicit `*.android.kt`, `*.ios.kt`, or `*.jvm.kt` files.
