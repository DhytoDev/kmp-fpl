package dev.dhyto.fpl.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Cookie
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.renderSetCookieHeader
import io.ktor.http.setCookie
import org.koin.core.component.KoinComponent

class FPLAuthenticationApi : KoinComponent {

    suspend fun authenticate(login: String, password: String): String {
        val loginUrl = "https://users.premierleague.com/accounts/login/"
        val redirectUrl = "https://fantasy.premierleague.com/"

        val request = HttpRequestBuilder().apply {
            url(loginUrl)
            method = HttpMethod.Post
            setBody(
                listOf(
                    "login" to login,
                    "password" to password,
                    "app" to "plfpl-web",
                    "redirect_uri" to redirectUrl
                ).formUrlEncode()
            )
            headers {
                append("accept-language", "en")
            }
        }

        val response = client.post(request)

        if (response.status != HttpStatusCode.Found) {
            throw FplApiException("Could not authenticate! Status code: ${response.status}")
        }

        val cookies = response.setCookie()

        val cookie = cookies.joinToString(separator = ";") { renderSetCookieHeader(it) }

        verifyAuthCookies(cookies, "sessionid", "pl_profile", "csrftoken")

        return cookie
    }

    private fun verifyAuthCookies(cookies: List<Cookie>, vararg cookieNames: String) {
        if (cookies.isEmpty()) {
            throw FplApiException("No cookies returned!")
        }

        for (cookieName in cookieNames) {
            val allCookies = cookies.sortedBy { it.domain }
                .joinToString("\n") { "[${it.domain}]${it.name} : ${it.value}" }

            if (!cookies.any { it.name == cookieName }) {
                val error = "Missing $cookieName cookie! Cookies:\n$allCookies"
                throw FplApiException(error)
            }
        }
    }

    companion object {
        const val USER_COOKIE_PREFS = "user_cookie_fpl"

        private val client = HttpClient(CIO).config {
            install(DefaultRequest) {
                contentType(ContentType.Application.FormUrlEncoded)
            }

            install(UserAgent) {
                agent =
                    "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.d(tag = "KtorClient", null) {
                            message
                        }
                    }
                }
            }
            followRedirects = false
        }
    }
}


class FplApiException(message: String) : Exception(message)