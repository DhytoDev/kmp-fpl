package dev.dhyto.fpl.shared.data.remote

import co.touchlab.kermit.Logger
import dev.dhyto.fpl.shared.data.remote.model.DreamTeamSquadDto
import dev.dhyto.fpl.shared.data.remote.model.EventStatusDto
import dev.dhyto.fpl.shared.data.remote.model.FixtureDto
import dev.dhyto.fpl.shared.data.remote.model.GeneralInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
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
