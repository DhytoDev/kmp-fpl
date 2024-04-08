package dev.dhyto.fpl.domain.usecases

import arrow.core.Either
import arrow.core.right
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.UpcomingOpponent
import dev.dhyto.fpl.domain.repositories.IFplRepository
import org.koin.core.component.KoinComponent

class GetThreeUpcomingFixtures(
    private val fplRepository: IFplRepository
) : KoinComponent {

    suspend fun invoke(playerId: Int, currentGameWeek: Int): Either<Failure, Map<Int, List<UpcomingOpponent>>> {

        val u = mutableMapOf<Int, List<UpcomingOpponent>>()

        return fplRepository.getPlayerDetails(playerId).map { playerSummary ->
            for (gw in currentGameWeek + 1..currentGameWeek + 3) {
                u[gw] = playerSummary.upcomingOpponents.filter {
                    it.gameWeek == gw
                }
            }

            return u.right()
        }
    }

}