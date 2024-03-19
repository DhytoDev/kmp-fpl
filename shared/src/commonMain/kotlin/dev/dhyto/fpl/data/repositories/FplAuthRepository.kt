package dev.dhyto.fpl.data.repositories

import arrow.core.Either
import com.russhwolf.settings.set
import dev.dhyto.fpl.data.local.KeyValuePersistence
import dev.dhyto.fpl.data.remote.FPLAuthenticationApi
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.repositories.IFplAuthRepository
import org.koin.core.component.KoinComponent

class FplAuthRepository(
    private val keyValuePersistence: KeyValuePersistence,
    private val fplAuthApi: FPLAuthenticationApi
) : IFplAuthRepository, KoinComponent {
    override suspend fun signIn(userName: String, password: String): Either<Failure, Unit> {
        return Either.catch {
            val result = fplAuthApi.authenticate(userName, password)

            keyValuePersistence.settings[FPLAuthenticationApi.USER_COOKIE_PREFS] = result

        }.mapLeft { Failure.NetworkFailure(it.message) }
    }
}