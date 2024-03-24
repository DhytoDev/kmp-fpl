package dev.dhyto.fpl.domain.usecases

import arrow.core.Either
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.domain.repositories.IFplRepository
import org.koin.core.component.KoinComponent

class GetMyTeam(
    private val fplRepository: IFplRepository,
) : KoinComponent {

    suspend fun invoke(): Either<Failure, List<ManagerEntry>> {
        return fplRepository.getMyTeam()
    }
}