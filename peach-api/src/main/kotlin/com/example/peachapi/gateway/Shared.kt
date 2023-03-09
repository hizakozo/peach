package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError

fun<T> Either<Throwable, T>.toUnExpectError(): Either<UnExpectError, T> =
    this.mapLeft { UnExpectError(it, it.message) }