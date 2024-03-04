package dev.dhyto.fpl.domain.repositories

import arrow.core.Either
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.Fixture
import dev.dhyto.fpl.domain.entities.ManagerInfo
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.domain.entities.Team

interface IFplRepository {
    suspend fun fetchAndCacheBootstrapStaticInfo(): Either<Failure, List<Player>>

    suspend fun getDreamTeamSquad(gameWeek: Int): Either<Failure, List<Player>>

    suspend fun getFixtures(gameWeek: Int): Either<Failure, List<Fixture>>

    suspend fun currentGameWeek(): Int

    suspend fun findTeamById(teamId: Int): Team

    suspend fun getManagerInfo(managerId: Int): Either<Failure, ManagerInfo>
}