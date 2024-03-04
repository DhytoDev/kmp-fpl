package dev.dhyto.fpl.shared.data.repositories

import arrow.core.Either
import arrow.core.getOrElse
import arrow.fx.coroutines.parZip
import dev.dhyto.fpl.shared.data.data_source.IFplDataSource
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.domain.entities.ManagerInfo
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.entities.Team
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
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

                    val teamHome = findTeamById(fixtureDto.teamH)
                    val teamAway = findTeamById(fixtureDto.teamA)

                    Fixture(
                        code = fixtureDto.code,
                        gameWeek = fixtureDto.event,
                        id = fixtureDto.id,
                        teamHome = teamHome,
                        teamAway = teamAway,
                        teamHScore = fixtureDto.teamHScore,
                        teamAScore = fixtureDto.teamAScore,
                        kickOffTime = fixtureDto.kickoffTime,
                    )
                }
            }
        }
    }

    override suspend fun currentGameWeek(): Int =
        fplDataSource.fetchEventStatus().getOrNull()?.status?.first()?.event ?: 1

    override suspend fun findTeamById(teamId: Int): Team =
        fplDataSource.findTeamById(teamId).getOrElse { Team(id = teamId) }

    override suspend fun getManagerInfo(managerId: Int): Either<Failure, ManagerInfo> =
        fplDataSource.fetchManagerInfo(managerId).map { it.toManagerInfo() }

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