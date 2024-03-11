package dev.dhyto.fpl.data.remote

import dev.dhyto.fpl.data.remote.model.DreamTeamSquadDto
import dev.dhyto.fpl.data.remote.model.EntriesDto
import dev.dhyto.fpl.data.remote.model.EventStatusDto
import dev.dhyto.fpl.data.remote.model.FixtureDto
import dev.dhyto.fpl.data.remote.model.GeneralInfoDto
import dev.dhyto.fpl.data.remote.model.ManagerInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import org.koin.core.component.KoinComponent

class FantasyPremierLeagueApi(
    private val client: HttpClient,
) : KoinComponent {

    companion object {
        private val TAG: String = FantasyPremierLeagueApi::class.simpleName.toString()
    }

    suspend fun fetchDreamTeam(eventId: Int) =
        client.get("dream-team/$eventId").body<DreamTeamSquadDto>()

    suspend fun fetchBootstrapStaticInfo() = client.get("bootstrap-static/").body<GeneralInfoDto>()

    suspend fun fetchEventStatus() = client.get("event-status/").body<EventStatusDto>()

    suspend fun fetchFixtures(eventId: Int?) =
        client.get("fixtures/?event=$eventId").body<List<FixtureDto>>()

    suspend fun fetchManagerInfo(managerId: Int) =
        client.get("entry/$managerId").body<ManagerInfoDto>()

    suspend fun fetchMyTeam(managerId: Int, cookie: String) =
        client.get {
            url("my-team/$managerId")
            headers {
                append("Cookie", cookie)
            }
        }.body<EntriesDto>()
}
