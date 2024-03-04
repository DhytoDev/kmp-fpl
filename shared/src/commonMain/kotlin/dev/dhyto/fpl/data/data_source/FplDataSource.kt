package dev.dhyto.fpl.shared.data.data_source

import arrow.core.Either
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import dev.dhyto.fpl.shared.FPLDatabase
import dev.dhyto.fpl.shared.data.local.mapper.mapToDomainTeam
import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.data.remote.model.DreamTeamSquadDto
import dev.dhyto.fpl.shared.data.remote.model.Element
import dev.dhyto.fpl.shared.data.remote.model.EventStatusDto
import dev.dhyto.fpl.shared.data.remote.model.FixtureDto
import dev.dhyto.fpl.shared.data.remote.model.GeneralInfoDto
import dev.dhyto.fpl.shared.data.remote.model.ManagerInfoDto
import dev.dhyto.fpl.shared.data.remote.model.TeamDto
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.base.Failure.NetworkFailure
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.entities.Team

interface IFplDataSource {
    suspend fun fetchBootstrapStaticInfo(): Either<Failure, GeneralInfoDto>

    suspend fun fetchManagerInfo(managerId: Int): Either<Failure, ManagerInfoDto>

    suspend fun fetchEventStatus(): Either<Failure, EventStatusDto>

    suspend fun fetchFixtures(gameWeek: Int): Either<Failure, List<FixtureDto>>

    suspend fun fetchDreamTeam(gameWeek: Int): Either<Failure, DreamTeamSquadDto>

    fun insertPlayer(element: Element)

    fun insertTeam(teamDto: TeamDto)

    suspend fun getAllPlayers(): Option<List<Player>>

    suspend fun findTeamById(teamId: Int): Option<Team>
}

class FplDataSource(
    private val fplApi: FantasyPremierLeagueApi,
    private val fplDb: FPLDatabase,
) : IFplDataSource {
    override suspend fun fetchBootstrapStaticInfo(): Either<Failure, GeneralInfoDto> {
        return Either.catch { fplApi.fetchBootstrapStaticInfo() }
            .mapLeft { NetworkFailure(it.message) }
    }

    override suspend fun fetchManagerInfo(managerId: Int): Either<Failure, ManagerInfoDto> {
        return Either.catch { fplApi.fetchManagerInfo(managerId) }
            .mapLeft { NetworkFailure(it.message) }
    }

    override suspend fun fetchEventStatus(): Either<Failure, EventStatusDto> {
        return Either.catch { fplApi.fetchEventStatus() }
            .mapLeft { NetworkFailure(it.message) }
    }

    override suspend fun fetchFixtures(gameWeek: Int): Either<Failure, List<FixtureDto>> {
        return Either.catch { fplApi.fetchFixtures(gameWeek) }
            .mapLeft { NetworkFailure(it.message) }
    }

    override suspend fun fetchDreamTeam(gameWeek: Int): Either<Failure, DreamTeamSquadDto> {
        return Either.catch { fplApi.fetchDreamTeam(gameWeek) }
            .mapLeft { NetworkFailure(it.message) }
    }

    override fun insertPlayer(element: Element) {
        fplDb.playerQueries.insertPlayer(
            id = element.id.toLong(),
            fullName = "${element.firstName} ${element.secondName}",
            displayName = element.webName,
            totalPoints = element.totalPoints.toLong(),
            price = (element.nowCost / 10).toDouble(),
            goalsScored = element.goalsScored.toLong(),
            assists = element.assists.toLong(),
            elementType = element.elementType.toLong(),
            code = element.code.toLong(),
            cleanSheets = element.cleanSheets.toLong(),
            saves = element.saves.toLong(),
            yellowCards = element.yellowCards.toLong(),
            redCards = element.redCards.toLong(),
            team = element.team.toLong(),
        )
    }

    override fun insertTeam(teamDto: TeamDto) {
        fplDb.teamQueries.insertTeam(
            id = teamDto.id.toLong(),
            name = teamDto.name,
            shortName = teamDto.shortName,
            code = teamDto.code.toLong()
        )
    }

    override suspend fun getAllPlayers(): Option<List<Player>> {
        val players = fplDb.playerQueries.getAllPlayers().executeAsList()

        if (players.isEmpty()) return none()

        return players.map { player ->
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
        }.some()
    }

    override suspend fun findTeamById(teamId: Int): Option<Team> {
        val team = fplDb.teamQueries.findTeamById(teamId.toLong()).executeAsOneOrNull()

        return team?.mapToDomainTeam()?.some() ?: none()
    }
}
