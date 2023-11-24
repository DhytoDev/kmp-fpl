package dev.dhyto.fpl.shared.data.remote

import dev.dhyto.fpl.shared.data.remote.model.DreamTeamSquadDto
import dev.dhyto.fpl.shared.data.remote.model.GeneralInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

class FantasyPremierLeagueApi(
    private val client: HttpClient,
    private val baseUrl: String = "https://fantasy.premierleague.com/api",
) : KoinComponent {

    suspend fun fetchDreamTeam(eventId: Int) =
        client.get("$baseUrl/dream-team/$eventId").body<DreamTeamSquadDto>()

    suspend fun fetchBootstrapStaticInfo() = client.get("$baseUrl/bootstrap-static/").body<GeneralInfoDto>()
}