package dev.dhyto.fpl.shared.data.repositories


import arrow.core.Either
import arrow.fx.coroutines.parZip
import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.base.Failure.NetworkFailure
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.KoinComponent

class FplRepository(
    private val fplApi: FantasyPremierLeagueApi,
) : IFplRepository, KoinComponent {
    override suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>> {
        val result = parZip(
            ctx = Dispatchers.IO,
            fa = { getAllPlayers() },
            fb = {
                Either.catch {
                    fplApi.fetchDreamTeam(gameWeek)
                }.mapLeft {
                    NetworkFailure(it.message)
                }.map { it.team }
            },
        ) { playerList, dreamTeamList ->
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

        return result
    }

    private suspend fun getAllPlayers(): Either<Failure, List<Player>> {
        return Either.catch { fplApi.fetchBootstrapStaticInfo() }
            .mapLeft { NetworkFailure(it.message) }.map {
                it.elements.map { player ->
                    val team = it.teams.first { team ->
                        team.id == player.team
                    }

                    Player(
                        id = player.id,
                        name = "${player.firstName} ${player.secondName}",
                        team = team.mapToDomainTeam(),
                        photoUrl = "${Player.basePhotoUrl}/p${player.code}.png",
                        points = player.totalPoints,
                        currentPrice = player.nowCost.toDouble(),
                        goalsScored = player.goalsScored,
                        assists = player.assists,
                        elementType = player.elementType,
                    )
                }
            }
    }
}