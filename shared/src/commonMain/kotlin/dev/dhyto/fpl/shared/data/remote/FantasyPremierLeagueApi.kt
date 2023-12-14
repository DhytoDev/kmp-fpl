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

    @OptIn(InternalAPI::class)
    suspend fun signIn() {
        try {
            val loginData = LoginData(
                "dhytodev@gmail.com",
                "@Gowa2106",
                "plfpl-web",
                "https://users.premierleague.com/",
            )

            val httpClient = HttpClient {
                followRedirects = true

                install(ContentNegotiation) {
//                    json(json)
                }

                install(Logging) {
                    logger = io.ktor.client.plugins.logging.Logger.DEFAULT
                    level = LogLevel.ALL
                    logger = object : io.ktor.client.plugins.logging.Logger {
                        override fun log(message: String) {
                            Logger.d(tag = "Login", null) {
                                message
                            }
                        }
                    }
                }
            }

            val loginResponse = httpClient.post("https://users.premierleague.com/accounts/login/") {
                body = FormDataContent(Parameters.build {
                    append("login", loginData.login)
                    append("password", loginData.password)
                    append("app", loginData.app)
                    append("redirect_uri", loginData.redirectUri)
                })
                headers {
                    append(
                        HttpHeaders.UserAgent,
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36"
                    )
                    append(HttpHeaders.AcceptLanguage, "en-GB,en;q=0.5")
                    append(
                        HttpHeaders.Accept,
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3 q=0.9"
                    )
                    append(
                        HttpHeaders.ContentType, "application/x-www-form-urlencoded; charset=UTF-8"
                    )
                    append(HttpHeaders.Origin, "https://users.premierleague.com")
                }
            }
            Logger.d(TAG, null) {
                "status: ${loginResponse.status}"
            }

            val sessionId =
                loginResponse.headers["set-cookie"]?.split(";")?.first()?.split("=")?.last()

            println("sessionId = $sessionId")
        } catch (e: Exception) {
            Logger.e(
                tag = FantasyPremierLeagueApi::class.simpleName.toString(),
                throwable = e.cause,
                messageString = e.message.toString()
            )
        }
    }


}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class LoginData(
    val login: String,
    val password: String,
    val app: String = "plfpl-web",
    @JsonNames("redirect_uri") val redirectUri: String = "https://fantasy.premierleague.com/",
)