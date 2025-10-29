# FPL API Reference

## Overview

The FPL App integrates with the official Fantasy Premier League API to fetch real-time data about
players, teams, fixtures, and user information. This document provides a comprehensive reference to
all API endpoints used in the application.

## Base Configuration

### Base URL

```
https://fantasy.premierleague.com/api/
```

### Authentication

Most endpoints are public, but user-specific data requires authentication via session cookies.

### HTTP Client Configuration

```kotlin
// NetworkModule.kt
defaultRequest {
    url("https://fantasy.premierleague.com/api/")
    header(HttpHeaders.Accept, ContentType.Application.Json)
    header(HttpHeaders.ContentType, ContentType.Application.Json)
}
```

## Public Endpoints

### Bootstrap Static Information

**Endpoint**: `GET /bootstrap-static/`  
**Purpose**: Fetches core game data including all players, teams, and general game information.  
**Authentication**: Not required  
**Implementation**: `FantasyPremierLeagueApi.fetchBootstrapStaticInfo()`

**Response Model**: `GeneralInfoDto`

```kotlin
@Serializable
data class GeneralInfoDto(
    val elements: List<ElementDto>,          // All players
    val teams: List<TeamDto>,               // All Premier League teams
    val element_types: List<ElementTypeDto>, // Position types (GK, DEF, MID, FWD)
    val events: List<EventDto>,             // Game weeks
    val phases: List<PhaseDto>,             // Game phases
    val total_players: Int                   // Total registered players
)
```

**Key Data**:

- **Elements**: Complete player database with stats, prices, teams
- **Teams**: All 20 Premier League teams with codes and names
- **Events**: Game week information and deadlines

### Dream Team

**Endpoint**: `GET /dream-team/{eventId}`  
**Purpose**: Fetches the official dream team for a specific game week.  
**Authentication**: Not required  
**Implementation**: `FantasyPremierLeagueApi.fetchDreamTeam(eventId)`

**Response Model**: `DreamTeamSquadDto`

```kotlin
@Serializable
data class DreamTeamSquadDto(
    val team: List<DreamTeamPlayerDto>
)

@Serializable
data class DreamTeamPlayerDto(
    val element: Int,           // Player ID
    val points: Int,           // Points scored this gameweek
    val position: Int          // Formation position
)
```

### Fixtures

**Endpoint**: `GET /fixtures/?event={eventId}`  
**Purpose**: Fetches fixture information for a specific game week.  
**Authentication**: Not required  
**Implementation**: `FantasyPremierLeagueApi.fetchFixtures(eventId)`

**Response Model**: `List<FixtureDto>`

```kotlin
@Serializable
data class FixtureDto(
    val id: Int?,
    val code: Int?,
    val event: Int?,                    // Game week
    @SerialName("team_h") val teamH: Int?,     // Home team ID
    @SerialName("team_a") val teamA: Int?,     // Away team ID
    @SerialName("team_h_score") val teamHScore: Int?, // Home team score
    @SerialName("team_a_score") val teamAScore: Int?, // Away team score
    @SerialName("kickoff_time") val kickoffTime: String?,
    val difficulty: Int?                // Fixture difficulty rating
)
```

### Event Status

**Endpoint**: `GET /event-status/`  
**Purpose**: Gets current game week status and information.  
**Authentication**: Not required  
**Implementation**: `FantasyPremierLeagueApi.fetchEventStatus()`

**Response Model**: `EventStatusDto`

```kotlin
@Serializable
data class EventStatusDto(
    val status: List<EventStatusItemDto>
)

@Serializable
data class EventStatusItemDto(
    val event: Int,             // Current game week
    @SerialName("bonus_added") val bonusAdded: Boolean,
    val date: String
)
```

### Manager Information

**Endpoint**: `GET /entry/{managerId}`  
**Purpose**: Fetches public information about a Fantasy Premier League manager.  
**Authentication**: Not required  
**Implementation**: `FantasyPremierLeagueApi.fetchManagerInfo(managerId)`

**Response Model**: `ManagerInfoDto`

```kotlin
@Serializable
data class ManagerInfoDto(
    val id: Int,
    @SerialName("player_first_name") val playerFirstName: String,
    @SerialName("player_last_name") val playerLastName: String,
    @SerialName("name") val teamName: String,
    @SerialName("summary_overall_points") val summaryOverallPoints: Int,
    @SerialName("summary_overall_rank") val summaryOverallRank: Int,
    @SerialName("summary_event_points") val summaryEventPoints: Int,
    @SerialName("summary_event_rank") val summaryEventRank: Int
)
```

### Player Details

**Endpoint**: `GET /element-summary/{playerId}`  
**Purpose**: Fetches detailed information about a specific player including fixtures and history.  
**Authentication**: Not required  
**Implementation**: `FantasyPremierLeagueApi.fetchPlayerDetails(playerId)`

**Response Model**: `PlayerSummaryDto`

```kotlin
@Serializable
data class PlayerSummaryDto(
    val fixtures: List<FixtureDto>,     // Upcoming fixtures
    val history: List<PlayerHistoryDto>, // Past performance
    val history_past: List<PlayerSeasonDto> // Previous seasons
)
```

## Authenticated Endpoints

### User Profile

**Endpoint**: `GET /me/`  
**Purpose**: Gets current authenticated user information.  
**Authentication**: Required (session cookie)  
**Implementation**: `FantasyPremierLeagueApi.fetchUserLoggedIn(cookie)`

**Headers**:

```kotlin
headers {
    append("Cookie", sessionCookie)
}
```

**Response Model**: `MeDto`

```kotlin
@Serializable
data class MeDto(
    val player: PlayerInfoDto
)
```

### My Team

**Endpoint**: `GET /my-team/{managerId}`  
**Purpose**: Fetches the authenticated user's current team selection.  
**Authentication**: Required (session cookie)  
**Implementation**: `FantasyPremierLeagueApi.fetchMyTeam(managerId, cookie)`

**Response Model**: `EntriesDto`

```kotlin
@Serializable
data class EntriesDto(
    val active_chip: String?,
    val automatic_subs: List<SubstitutionDto>?,
    val entry_history: EntryHistoryDto?,
    val picks: List<PickDto>?
)

@Serializable
data class PickDto(
    val element: Int?,          // Player ID
    val position: Int?,         // Position in formation (1-11 starting, 12-15 bench)
    @SerialName("is_captain") val isCaptain: Boolean?,
    @SerialName("is_vice_captain") val isViceCaptain: Boolean?,
    val multiplier: Int?        // Points multiplier (1 for normal, 2 for captain, 3 for triple captain)
)
```

### Save Team Changes

**Endpoint**: `POST /my-team/{managerId}`  
**Purpose**: Saves changes to the authenticated user's team.  
**Authentication**: Required (session cookie)  
**Implementation**: `FantasyPremierLeagueApi.saveTeamPicks(managerId, cookie, teamPicks)`

**Request Body**: `EntriesDto` (same as GET response)  
**Response**: Updated `EntriesDto`

## Authentication Flow

### Login Process

The app uses the FPL Authentication API for user login:

**Endpoint**: `POST /login/`  
**Implementation**: `FPLAuthenticationApi.authenticateUser(email, password)`

```kotlin
class FPLAuthenticationApi(private val httpClientEngine: HttpClientEngine) {
    suspend fun authenticateUser(email: String, password: String): Either<Failure, String> {
        // Implementation handles form data submission and cookie extraction
    }
}
```

**Request Format**: Form data (not JSON)

```
email: user@example.com
password: userpassword
```

**Success Response**: Session cookie for subsequent authenticated requests

## Data Transformation

### Domain Mapping

The app transforms API DTOs into domain entities:

```kotlin
// Player DTO to Domain Entity
fun ElementDto.toDomain(team: Team): Player {
    return Player(
        id = id,
        name = webName ?: "",
        displayName = webName ?: "",
        elementType = elementType,
        points = eventPoints ?: 0,
        team = team,
        playerStats = PlayerStats(
            goalsScored = goalsScored ?: 0,
            assists = assists ?: 0,
            cleanSheets = cleanSheets ?: 0,
            // ... other stats
        ),
        playerPosition = elementType?.getPlayerPosition()
    )
}
```

### Error Handling

All API calls are wrapped with proper error handling:

```kotlin
suspend fun fetchBootstrapStaticInfo(): Either<Failure, GeneralInfoDto> {
    return try {
        val response = api.fetchBootstrapStaticInfo()
        response.right()
    } catch (e: HttpExceptions) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> UnauthenticatedFailure().left()
            HttpStatusCode.RequestTimeout -> NetworkFailure("Request timeout").left()
            else -> NetworkFailure(e.message).left()
        }
    }
}
```

## Response Validation

### HTTP Response Validator

```kotlin
HttpResponseValidator {
    validateResponse { response ->
        if (!response.status.isSuccess()) {
            val failureReason = when (response.status) {
                HttpStatusCode.Unauthorized -> "Unauthorized"
                HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                HttpStatusCode.NotFound -> "Invalid Request"
                HttpStatusCode.RequestTimeout -> "Network Timeout"
                in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout ->
                    "${response.status.value} Server Error"
                else -> "Network error!"
            }
            throw HttpExceptions(response, failureReason, response.bodyAsText())
        }
    }
}
```

## Caching Strategy

### Local Database Integration

Critical data is cached locally using SQLDelight:

```sql
-- Player.sq
CREATE TABLE playerEntity (
    id INTEGER NOT NULL PRIMARY KEY,
    fullName TEXT NOT NULL,
    displayName TEXT NOT NULL,
    totalPoints INTEGER NOT NULL,
    price REAL NOT NULL,
    team INTEGER NOT NULL
    -- ... other fields
);

insertPlayer:
INSERT OR REPLACE INTO playerEntity(...) VALUES (...);

getAllPlayers:
SELECT * FROM playerEntity;
```

### Repository Pattern

```kotlin
class FplRepository(private val fplDataSource: IFplDataSource) : IFplRepository {
    override suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>> {
        return parZip(
            fa = {
                // Try cache first, fallback to API
                fplDataSource.getAllPlayers()
                    .getOrElse { fetchAndCacheBootstrapStaticInfo().getOrElse { emptyList() } }
            },
            fb = {
                fplDataSource.fetchDreamTeam(gameWeek).map { it.team }
            }
        ) { playerList, dreamTeamList ->
            // Combine cached players with live dream team data
            dreamTeamList.map { dreamTeam ->
                dreamTeam.map { dt ->
                    val player = playerList.first { it.id == dt.element }
                    player.copy(points = dt.points)
                }
            }
        }
    }
}
```

## Rate Limiting and Best Practices

### Request Throttling

- Avoid excessive API calls by caching data locally
- Use parallel requests (`parZip`) for independent data fetching
- Implement proper retry mechanisms for transient failures

### Performance Optimization

```kotlin
// Use coroutines for concurrent API calls
parZip(
    ctx = Dispatchers.IO,
    fa = { fetchPlayers() },
    fb = { fetchFixtures() },
    fc = { fetchDreamTeam() }
) { players, fixtures, dreamTeam ->
    // Process combined data
}
```

## Testing API Integration

### Mock API Responses

```kotlin
class FakeFantasyPremierLeagueApi : FantasyPremierLeagueApi {
    override suspend fun fetchBootstrapStaticInfo(): GeneralInfoDto {
        return GeneralInfoDto(
            elements = listOf(/* test players */),
            teams = listOf(/* test teams */),
            // ... test data
        )
    }
}
```

### Integration Tests

```kotlin
@Test
fun `fetchDreamTeam returns expected data structure`() = runTest {
        val result = api.fetchDreamTeam(gameWeek = 1)

        result.shouldBeRight()
        result.getOrNull()?.team?.shouldNotBeEmpty()
    }
```

## Troubleshooting

### Common Issues

1. **401 Unauthorized**: Session cookie expired, re-authenticate user
2. **404 Not Found**: Invalid manager ID or non-existent resource
3. **429 Rate Limited**: Too many requests, implement backoff strategy
4. **500 Server Error**: FPL API temporary issues, retry with exponential backoff

### Debug Network Calls

Enable network logging to debug API issues:

```kotlin
val enableNetworkLogs = true // Set in debug builds
```

Check logs with tag `"KtorClient"` for detailed request/response information.

---

For implementation details, see the source files in
`shared/src/commonMain/kotlin/dev/dhyto/fpl/data/remote/`.