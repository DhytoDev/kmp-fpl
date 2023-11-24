package dev.dhyto.fpl.shared.domain.repositories

import dev.dhyto.fpl.shared.domain.entities.Player

interface IFplRepository {
    suspend fun getDreamTeamSquad(gameWeek: Int): List<Player>
}