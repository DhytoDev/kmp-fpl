package dev.dhyto.fpl.data.repositories

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrElse
import arrow.core.right
import arrow.fx.coroutines.parZip
import dev.dhyto.fpl.data.data_source.IFplDataSource
import dev.dhyto.fpl.data.remote.model.EntriesDto
import dev.dhyto.fpl.data.repositories.TeamPicksMapper.toManagerEntry
import dev.dhyto.fpl.data.repositories.TeamPicksMapper.toPickDto
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.Fixture
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.entities.ManagerInfo
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.domain.entities.PlayerSummary
import dev.dhyto.fpl.domain.entities.Team
import dev.dhyto.fpl.domain.entities.UpcomingOpponent
import dev.dhyto.fpl.domain.repositories.IFplRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class FplRepository(
    private val fplDataSource: IFplDataSource,
) : IFplRepository, KoinComponent {

    override suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>> {
        return parZip(
            ctx = Dispatchers.IO,
            fa = {
                fplDataSource.getAllPlayers()
                    .getOrElse {
                        fetchAndCacheBootstrapStaticInfo().getOrElse { emptyList() }
                    }

            }, fb = {
                fplDataSource.fetchDreamTeam(gameWeek).map { it.team }
            }
        ) { playerList, dreamTeamList ->
            dreamTeamList.map {
                val dreamTeamEleven = it.map { d ->
                    val player = playerList.first { player ->
                        player.id == d.element
                    }
                    player.copy(points = d.points)
                }
                dreamTeamEleven
            }
        }
    }

    override suspend fun getFixtures(gameWeek: Int): Either<Failure, List<Fixture>> {
        return withContext(Dispatchers.IO) {
            fplDataSource.fetchFixtures(gameWeek).map {
                return@map it.map { fixtureDto ->

                    val teamHome = findTeamById(fixtureDto.teamH!!)
                    val teamAway = findTeamById(fixtureDto.teamA!!)

                    Fixture(
                        code = fixtureDto.code!!,
                        gameWeek = fixtureDto.event,
                        id = fixtureDto.id,
                        teamHome = teamHome,
                        teamAway = teamAway,
                        teamHScore = fixtureDto.teamHScore,
                        teamAScore = fixtureDto.teamAScore,
                        kickOffTime = fixtureDto.kickoffTime,
                        difficulty = fixtureDto.difficulty,
                        isHome = false
                    )
                }
            }
        }
    }

    override suspend fun currentGameWeek(): Int {
        return fplDataSource.fetchEventStatus().map {
            if (it.status.isEmpty()) return@map 1 else fplDataSource.fetchEventStatus().getOrNull()?.status?.first()?.event ?: 1
        }.getOrElse { 1 }

    }

    override suspend fun findTeamById(teamId: Int): Team =
        fplDataSource.findTeamById(teamId).getOrElse { Team(id = teamId) }

    override suspend fun getManagerInfo(managerId: Int): Either<Failure, ManagerInfo> =
        fplDataSource.fetchManagerInfo(managerId).map { it.toManagerInfo() }

    override suspend fun getMyTeam(): Either<Failure, List<ManagerEntry>> {
        return parZip(
            ctx = Dispatchers.IO,
            fa = {
                fplDataSource.getAllPlayers()
                    .getOrElse {
                        fetchAndCacheBootstrapStaticInfo().getOrElse { emptyList() }
                    }
            },
            fb = {
                fplDataSource.getMyTeam()
            }
        ) { players, entries ->
            entries.map { entriesDto ->
                entriesDto.picks!!.map { pickDto ->
                    val player = players.first { it.id == pickDto?.element }

                    pickDto.toManagerEntry(player)
                }
            }
        }
    }

    override suspend fun saveMyTeamPicks(teamPicks: List<ManagerEntry>): Either<Failure, List<ManagerEntry>> {

        val picks = teamPicks.map { it.toPickDto() }

        val entries = EntriesDto(null, null, null, picks)

        return fplDataSource.saveTeamPicks(entries).flatMap { getMyTeam() }
    }

    override suspend fun getPlayerDetails(playerId: Int): Either<Failure, PlayerSummary> {
        return fplDataSource.getPlayerDetails(playerId).map { ps ->
            return PlayerSummary(
                upcomingOpponents = ps.fixtures.map {
                    val team =
                        if (it.isHome == true) findTeamById(it.teamA!!) else findTeamById(it.teamH!!)

                    UpcomingOpponent(
                        gameWeek = it.event,
                        team = team,
                        difficulty = it.difficulty,
                    )
                }
            ).right()
        }
    }

    override suspend fun fetchAndCacheBootstrapStaticInfo(): Either<Failure, List<Player>> {
        return fplDataSource.fetchBootstrapStaticInfo()
            .map { generalInfoDto ->
                generalInfoDto.teams.forEach { teamDto ->
                    fplDataSource.insertTeam(teamDto)
                }

                generalInfoDto.elements.forEach { element -> fplDataSource.insertPlayer(element) }

                return@map fplDataSource.getAllPlayers().getOrNull() ?: emptyList<Player>()
            }
    }
}