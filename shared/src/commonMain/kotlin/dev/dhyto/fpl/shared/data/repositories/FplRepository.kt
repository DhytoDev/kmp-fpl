package dev.dhyto.fpl.shared.data.repositories


import arrow.core.Either
import arrow.fx.coroutines.parZip
import co.touchlab.kermit.Logger
import dev.dhyto.fpl.shared.FPLDatabase
import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.data.remote.model.EventStatusDto
import dev.dhyto.fpl.shared.data.sqlDelight.mapper.mapToDomainTeam
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.base.Failure.NetworkFailure
import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class FplRepository(
    private val fplApi: FantasyPremierLeagueApi,
    private val fplDb: FPLDatabase,
) : IFplRepository, KoinComponent {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var _currentGameWeek: MutableStateFlow<Int> = MutableStateFlow(1)

    override val currentGameWeek = _currentGameWeek.asStateFlow()

    init {
        coroutineScope.launch {
            getCurrentGameWeek()
        }
    }

    override suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>> {
        return withContext(Dispatchers.IO) {
            parZip(ctx = Dispatchers.IO, fa = { getAllPlayers() }, fb = {
                Either.catch {
                    fplApi.fetchDreamTeam(gameWeek)
                }.mapLeft {
                    NetworkFailure(it.message)
                }.map {
                    it.team
                }
            }) { playerList, dreamTeamList ->
                val players = playerList.getOrNull() ?: emptyList()

                dreamTeamList.map {
                    val dreamTeamEleven = it.map { d ->
                        val player = players.first { player ->
                            player.id == d.element
                        }
                        player.copy(points = d.points)
                    }
                    dreamTeamEleven
                }
            }
        }
    }

    override suspend fun getFixtures(gameWeek: Int): Either<Failure, List<Fixture>> {
        return Either.catch {
            fplApi.fetchFixtures(gameWeek)
        }.mapLeft {
            NetworkFailure(it.message)
        }.map {
            return@map it.map { fixtureDto ->
                val teamHome =
                    fplDb.teamQueries.findTeamById(fixtureDto.teamH.toLong()).executeAsOne()
                val teamAway =
                    fplDb.teamQueries.findTeamById(fixtureDto.teamA.toLong()).executeAsOne()

                Fixture(
                    code = fixtureDto.code,
                    gameWeek = fixtureDto.event,
                    id = fixtureDto.id,
                    teamHome = teamHome.mapToDomainTeam(),
                    teamAway = teamAway.mapToDomainTeam(),
                    teamHScore = fixtureDto.teamHScore,
                    teamAScore = fixtureDto.teamAScore
                )
            }
        }
    }

    private suspend fun getCurrentGameWeek() {
        val currentGameWeek = getEventGameWeekStatus().map {
            it.status.first().event
        }.getOrNull() ?: 1

        Logger.d("currentGameWeek: $currentGameWeek")
        _currentGameWeek.emit(currentGameWeek)
    }

    private suspend fun getEventGameWeekStatus(): Either<Failure, EventStatusDto> {
        return Either.catch {
            fplApi.fetchEventStatus()
        }.mapLeft { NetworkFailure(it.message) }
    }

    private suspend fun fetchAndCacheBootstrapStaticInfo(): Either<Failure, Unit> {
        return Either.catch { fplApi.fetchBootstrapStaticInfo() }
            .mapLeft { NetworkFailure(it.message) }.map { generalInfoDto ->
                generalInfoDto.teams.forEach { teamDto ->
                    fplDb.teamQueries.insertTeam(
                        teamDto.id.toLong(), teamDto.name, teamDto.shortName, teamDto.code.toLong()
                    )
                }

                generalInfoDto.elements.forEach { element ->
                    fplDb.playerQueries.insertPlayer(
                        element.id.toLong(),
                        "${element.firstName} ${element.secondName}",
                        element.webName,
                        element.totalPoints.toLong(),
                        (element.nowCost / 10).toDouble(),
                        element.goalsScored.toLong(),
                        element.assists.toLong(),
                        element.elementType.toLong(),
                        element.code.toLong(),
                        element.cleanSheets.toLong(),
                        element.saves.toLong(),
                        element.yellowCards.toLong(),
                        element.redCards.toLong(),
                        element.team.toLong(),
                    )
                }
            }
    }

    private suspend fun getAllPlayers(): Either<Failure, List<Player>> {
        return withContext(Dispatchers.IO) {
            Either.catch {
                fplDb.playerQueries.getAllPlayers()
            }.mapLeft {
                Failure.LocalFailure(it.message)
            }.map { players ->
                var playerList = players.executeAsList()

                Logger.i("${playerList.isEmpty()}", null, "PlayerList")

                if (playerList.isEmpty()) {
                    fetchAndCacheBootstrapStaticInfo()
                    playerList = fplDb.playerQueries.getAllPlayers().executeAsList()
                }

                playerList.map { player ->
                    val team = fplDb.teamQueries.findTeamById(player.team).executeAsOne()

                    Player(
                        id = player.id.toInt(),
                        name = player.fullName,
                        displayName = player.displayName,
                        team = team.mapToDomainTeam(),
                        photoUrl = "${Player.basePhotoUrl}/p${player.code}.png",
                        points = player.totalPoints.toInt(),
                        price = player.price,
                        goalsScored = player.goalsScored.toInt(),
                        assists = player.assists.toInt(),
                        elementType = player.elementType?.toInt(),
                    )
                }
            }

        }
    }
}