package dev.dhyto.fpl.shared.domain.repositories

import arrow.core.Either
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.domain.entities.Player
import kotlinx.coroutines.flow.StateFlow

interface IFplRepository {
    val currentGameWeek: StateFlow<Int>
    suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>>

    suspend fun getFixtures(gameWeek: Int): Either<Failure, List<Fixture>>
}