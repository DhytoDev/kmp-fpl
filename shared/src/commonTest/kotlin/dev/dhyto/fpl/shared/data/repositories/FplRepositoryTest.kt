package dev.dhyto.fpl.shared.data.repositories

import dev.dhyto.fpl.shared.FPLDatabase
import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.data.repositories.ApiMock.givenSuccess
import dev.dhyto.fpl.shared.data.repositories.ApiMock.mockEngine
import dev.dhyto.fpl.shared.data.testDbDriverFactory
import dev.dhyto.fpl.shared.di.createHttpClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class FplRepositoryTest {

    private val fplDb = FPLDatabase(testDbDriverFactory())

    private val client = createHttpClient(mockEngine.create(), false)

    private val fplAPi = FantasyPremierLeagueApi(client)

    @Test
    fun getAllPlayers() = runTest {
        val repository = FplRepository(fplAPi, fplDb).apply {
            givenSuccess()
            fetchAndCacheBootstrapStaticInfo()
        }

        val result = repository.getAllPlayers()

        assertEquals(1, result.getOrNull()?.size)
    }

    @Test
    fun getDreamTeamSquadFromCache() = runTest {
        val repository = FplRepository(fplAPi, fplDb).apply {
            givenSuccess()
        }

        val result = repository.getDreamTeamSquad(1)

        assertEquals(true, result.isRight())
        assertEquals("Erling Haaland", result.getOrNull()?.first()?.name)

        /**
        * for the second invoke, fetchAndCacheBootstrapStaticInfo should not be called again
        * players data should be got from the cache
        */
        repository.getDreamTeamSquad(1)

        assertEquals(false, repository.getAllPlayers().getOrNull().isNullOrEmpty())
    }
}