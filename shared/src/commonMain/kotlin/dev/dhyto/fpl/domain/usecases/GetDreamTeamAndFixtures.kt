package dev.dhyto.fpl.shared.domain.usecases

import arrow.core.Either
import arrow.fx.coroutines.parZip
import co.touchlab.kermit.Logger
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.domain.entities.Fixture
import dev.dhyto.fpl.shared.domain.entities.Player
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.KoinComponent
import kotlin.time.measureTime

class GetDreamTeamAndFixtures(
    private val fplRepository: IFplRepository,
) : KoinComponent {

    suspend fun invoke(): Pair<Either<Failure, List<Player>>, Either<Failure, List<Fixture>>> {
        val currentGameWeek = fplRepository.currentGameWeek()

        var result: Pair<Either<Failure, List<Player>>, Either<Failure, List<Fixture>>>

        val duration = measureTime {
            result = parZip(
                ctx = Dispatchers.IO,
                fa = {
                    fplRepository.getDreamTeamSquad(currentGameWeek)
                },
                fb = {
                    fplRepository.getFixtures(currentGameWeek)
                }
            ) { fa, fb ->
                val fixtures = fb.map {
                    it.map { f ->
                        var fixture = f

                        if (f.teamHome.name.isBlank()) {
                            val teamHome = fplRepository.findTeamById(f.teamHome.id!!)
                            val teamAway = fplRepository.findTeamById(f.teamAway.id!!)

                            fixture = fixture.copy(teamAway = teamAway, teamHome = teamHome)
                        }

                        fixture
                    }
                }

                fa to fixtures
            }
        }
        Logger.i(duration.toString())

        return result
    }
}

