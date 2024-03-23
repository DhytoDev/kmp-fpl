package dev.dhyto.fpl.shared.data.repositories


import dev.dhyto.fpl.FPLDatabase
import dev.dhyto.fpl.data.data_source.FplDataSource
import dev.dhyto.fpl.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.data.repositories.FplRepository
import dev.dhyto.fpl.di.createHttpClient
import dev.dhyto.fpl.shared.data.model.ApiMock.givenFailure
import dev.dhyto.fpl.shared.data.model.ApiMock.givenSuccess
import dev.dhyto.fpl.shared.data.model.ApiMock.mockEngine
import dev.dhyto.fpl.shared.data.testDbDriverFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class FplRepositoryTest {

    private val fplDb = FPLDatabase(testDbDriverFactory())

    private val client = createHttpClient(mockEngine.create(), false)

    private val fplAPi = FantasyPremierLeagueApi(client)

    private val fplDataSource = FplDataSource(fplAPi, fplDb)

    @Test
    fun getDreamTeamSquadFromCache() = runTest {
        val repository = FplRepository(fplDataSource).apply {
            givenSuccess()
        }

        val result = repository.getDreamTeamSquad(21)

        assertEquals(true, result.isRight())
        assertEquals("Erling Haaland", result.getOrNull()?.first()?.name)

        /**
         * for the second invoke, fetchAndCacheBootstrapStaticInfo should not be called again
         * players data should be got from the cache
         */
        repository.getDreamTeamSquad(21)

//        assertEquals(false, repository.getAllPlayers().getOrNull().isNullOrEmpty())
    }

    @Test
    fun getDreamTeamSquadReturnsFailure() = runTest {
        val repository = FplRepository(fplDataSource).apply {
            givenFailure()
        }

        val result = repository.getDreamTeamSquad(21)

        assertEquals(true, result.isLeft())
    }

    @Test
    fun getCurrentGameWeek() = runTest {
        val repository = FplRepository(fplDataSource).apply {
            givenSuccess()
        }

        assertEquals(21, repository.currentGameWeek())
    }

    @Test
    fun getCurrentGameWeekReturnsFailure() = runTest {
        val repository = FplRepository(fplDataSource).apply {
            givenFailure()
        }

        assertEquals(1, repository.currentGameWeek())
    }
}