package dev.dhyto.fpl.shared.domain.repositories

import arrow.core.Either
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.entities.Player
import kotlinx.coroutines.flow.Flow

interface IFplRepository {
    suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>>
}