package dev.dhyto.fpl.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun createHttpClient(httpClientEngine: HttpClientEngine, enableNetworkLogs: Boolean): HttpClient =
    HttpClient(httpClientEngine){
        expectSuccess = true

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                isLenient = true
                prettyPrint = true
                encodeDefaults = true
                classDiscriminator = "#class"
            })
        }

        defaultRequest {
            url("https://fantasy.premierleague.com/api/")
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        HttpResponseValidator {
            validateResponse { response ->
                if (!response.status.isSuccess()) {
                    val failureReason = when (response.status) {
                        HttpStatusCode.Unauthorized -> "Unauthorized"
                        HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                        HttpStatusCode.NotFound -> "Invalid Request"
                        HttpStatusCode.RequestTimeout -> "Network Timeout"
                        in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout ->
                            "${response.status.value} Server Error"

                        else -> "Network error!"
                    }


                    throw HttpExceptions(
                        response = response,
                        failureReason = failureReason,
                        cachedResponseText = response.bodyAsText()
                    )

                }
            }
        }

        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.d(tag = "KtorClient", null) { message }
                    }
                }
            }
        }
    }

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String,
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}

expect fun createHttpClientEngine(): HttpClientEngine

