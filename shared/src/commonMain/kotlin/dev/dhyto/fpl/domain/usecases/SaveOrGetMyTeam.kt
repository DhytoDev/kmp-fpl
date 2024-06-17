package dev.dhyto.fpl.domain.usecases

import arrow.core.Either
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.repositories.IFplRepository
import org.koin.core.component.KoinComponent

class SaveOrGetMyTeam(
    private val fplRepository: IFplRepository,
) : KoinComponent {

    suspend fun invoke(entries: List<ManagerEntry>? = null): Either<Failure, List<ManagerEntry>> {
        return if (entries == null) fplRepository.getMyTeam().map {
            it.map { managerEntry ->
                managerEntry.copy(isStarter = managerEntry.position <= 11)
            }
        } else fplRepository.saveMyTeamPicks(entries)
    }
}