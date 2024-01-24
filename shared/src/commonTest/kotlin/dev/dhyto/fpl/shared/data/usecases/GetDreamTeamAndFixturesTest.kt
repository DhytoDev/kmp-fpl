package dev.dhyto.fpl.shared.data.usecases

import dev.dhyto.fpl.shared.FPLDatabase
import dev.dhyto.fpl.shared.data.model.ApiMock
import dev.dhyto.fpl.shared.data.model.ApiMock.givenFailure
import dev.dhyto.fpl.shared.data.model.ApiMock.givenSuccess
import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.data.repositories.FplRepository
import dev.dhyto.fpl.shared.data.testDbDriverFactory
import dev.dhyto.fpl.shared.di.createHttpClient
import dev.dhyto.fpl.shared.domain.usecases.GetDreamTeamAndFixtures
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetDreamTeamAndFixturesTest {

    private val fplDb = FPLDatabase(testDbDriverFactory())

    private val client = createHttpClient(ApiMock.mockEngine.create(), false)

    private val fplAPi = FantasyPremierLeagueApi(client)

    private val fplRepository = FplRepository(fplAPi, fplDb)

    @Test
    fun getDreamTeamAndFixtures() = runTest {
        givenSuccess()

        val sut = GetDreamTeamAndFixtures(fplRepository)
        val result = sut.invoke()

        assertNotNull(result.first.getOrNull())
        assertNotNull(result.second.getOrNull())
    }

    @Test
    fun getDreamTeamAndFixturesReturnsFailure() = runTest {
        givenFailure()

        val sut = GetDreamTeamAndFixtures(fplRepository)
        val result = sut.invoke()

        assertEquals(true, result.first.isLeft())
        assertEquals(true, result.second.isLeft())
    }
}