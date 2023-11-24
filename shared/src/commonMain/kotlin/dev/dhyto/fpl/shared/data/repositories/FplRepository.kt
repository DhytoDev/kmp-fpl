package dev.dhyto.fpl.shared.data.repositories

import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import org.koin.core.component.KoinComponent

class FplRepository(
    private val fplApi: FantasyPremierLeagueApi,
) : IFplRepository, KoinComponent {
    override suspend fun getDreamTeamSquad(gameWeek: Int): List<Player> {
        val dreamTeamSquad = mutableListOf<Player>()

        val players = fplApi.fetchBootstrapStaticInfo().elements

        val dreamTeam = fplApi.fetchDreamTeam(gameWeek)

        for (dreamTeamMember in dreamTeam.team) {
            val player = players.first {
                it.id == dreamTeamMember.element
            }
            dreamTeamSquad.add(
                Player(
                    id = player.id,
                    name = "${player.firstName} ${player.secondName}",
                    team = "${player.team}",
                    points = dreamTeamMember.points,
                )
            )
        }

        return dreamTeamSquad
    }
}