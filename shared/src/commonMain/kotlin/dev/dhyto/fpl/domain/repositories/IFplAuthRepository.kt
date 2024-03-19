package dev.dhyto.fpl.domain.repositories

import arrow.core.Either
import dev.dhyto.fpl.domain.base.Failure

interface IFplAuthRepository {
    suspend fun signIn(userName: String, password: String) : Either<Failure, Unit>
}