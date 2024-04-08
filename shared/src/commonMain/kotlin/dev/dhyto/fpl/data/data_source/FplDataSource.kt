package dev.dhyto.fpl.data.data_source

import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.none
import arrow.core.some
import com.russhwolf.settings.get
import dev.dhyto.fpl.FPLDatabase
import dev.dhyto.fpl.data.local.KeyValuePersistence
import dev.dhyto.fpl.data.local.mapper.mapToDomain
import dev.dhyto.fpl.data.local.mapper.mapToDomainTeam
import dev.dhyto.fpl.data.remote.FPLAuthenticationApi
import dev.dhyto.fpl.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.data.remote.model.DreamTeamSquadDto
import dev.dhyto.fpl.data.remote.model.Element
import dev.dhyto.fpl.data.remote.model.EntriesDto
import dev.dhyto.fpl.data.remote.model.EventStatusDto
import dev.dhyto.fpl.data.remote.model.FixtureDto
import dev.dhyto.fpl.data.remote.model.GeneralInfoDto
import dev.dhyto.fpl.data.remote.model.ManagerInfoDto
import dev.dhyto.fpl.data.remote.model.PlayerSummaryDto
import dev.dhyto.fpl.data.remote.model.TeamDto
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.base.Failure.NetworkFailure
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.domain.entities.Team


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

    suspend fun getMyTeam(): Either<Failure, EntriesDto>

    suspend fun getPlayerDetails(playerId: Int): Either<Failure, PlayerSummaryDto>
}

class FplDataSource(
    private val fplApi: FantasyPremierLeagueApi,
    private val fplDb: FPLDatabase,
    private val fplPrefs: KeyValuePersistence
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

            player.mapToDomain(team)
        }.some()
    }

    override suspend fun findTeamById(teamId: Int): Option<Team> {
        val team = fplDb.teamQueries.findTeamById(teamId.toLong()).executeAsOneOrNull()

        return team?.mapToDomainTeam()?.some() ?: none()
    }

    override suspend fun getMyTeam(): Either<Failure, EntriesDto> {
        val cookie = fplPrefs.settings.get<String>(FPLAuthenticationApi.USER_COOKIE_PREFS)
        val managerId = fplPrefs.settings.get<Int>(FPLAuthenticationApi.MANAGER_ID_PREFS)

        if (cookie == null || managerId == null) {
            return Failure.UnauthenticatedFailure().left()
        }

        return Either.catch {
            fplApi.fetchMyTeam(managerId, cookie)
        }.mapLeft {
            return NetworkFailure(it.message).left()
        }
    }

    override suspend fun getPlayerDetails(playerId: Int): Either<Failure, PlayerSummaryDto> {

        return Either.catch {
            fplApi.fetchPlayerDetails(playerId)
        }.mapLeft {
            return NetworkFailure(it.message).left()
        }
    }
}