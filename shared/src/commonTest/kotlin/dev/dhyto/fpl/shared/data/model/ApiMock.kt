package dev.dhyto.fpl.shared.data.model

import co.touchlab.kermit.Logger
import dev.dhyto.fpl.shared.data.remote.model.DreamTeamMember
import dev.dhyto.fpl.shared.data.remote.model.DreamTeamSquadDto
import dev.dhyto.fpl.shared.data.remote.model.TopPlayer
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.http.hostWithPort
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ApiMock {
    private var isSuccess: Boolean? = null
        get() = field ?: throw IllegalStateException("Mock has not beet initialized")

    private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.urlWithoutPath: String get() = "${protocol.name}://$hostWithPortIfRequired"
    private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

    fun givenSuccess() {
        isSuccess = true
    }

    fun givenFailure() {
        isSuccess = false
    }

    val mockEngine = MockEngine.config {
        // Erling Haaland
        val dreamTeamSquad = DreamTeamSquadDto(
            listOf(DreamTeamMember(355, 10, 4)),
            TopPlayer(355, 10)
        )

        addHandler { req ->
            Logger.d("fullUrl: ${req.url.fullUrl}")

            val headers = headersOf(
                "Content-Type", ContentType.Application.Json.toString()
            )

            if (isSuccess == true) {
                when (req.url.encodedPath) {
                    "/api/bootstrap-static/" -> {
                        respond(
                            content = MockGeneralInfo.SUCCESS,
                            status = HttpStatusCode.OK,
                            headers = headers
                        )
                    }
                    "/api/dream-team/21" -> {
                        respond(
                            content = Json.encodeToString(
                                serializer = DreamTeamSquadDto.serializer(),
                                value = dreamTeamSquad
                            ),
                            status = HttpStatusCode.OK,
                            headers = headers
                        )
                    }
                    "/api/event-status/" -> {
                        respond(
                            content = MockEventStatus.SUCCESS,
                            status = HttpStatusCode.OK,
                            headers = headers
                        )
                    }
                    "/api/fixtures/" -> {
                        respond(
                            content = MockFixture.SUCCESS,
                            status = HttpStatusCode.OK,
                            headers = headers
                        )
                    }
                    else -> {
                        error("Unhandled : ${req.url.fullUrl}")
                    }
                }
            } else {
                respondError(
                    status = HttpStatusCode.InternalServerError, headers = headers,
                    content = """
                        {
                            "status": 500,
                            "message": "Internal Server Error"
                        }
                    """.trimIndent()
                )
            }
        }
    }
}

