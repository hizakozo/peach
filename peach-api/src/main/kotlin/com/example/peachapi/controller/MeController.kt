package com.example.peachapi.controller

import arrow.core.computations.either
import com.example.peachapi.config.AuthenticatedUser
import com.example.peachapi.domain.ApiException
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.reactive.asFlow
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class MeController {

    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

    suspend fun getMe(request: ServerRequest): ServerResponse =
        either<ApiException, AuthenticatedUser> {
            userContext().let { context ->
                context.authentication.details as AuthenticatedUser
            }
        }.fold(
            { it.toResponse() },
            { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
        )
}

data class MeResponse(
    val userId: String,
    val userName: String,
    val email: String,
)

fun AuthenticatedUser.toResponse(): MeResponse =
    MeResponse(
        this.userId.value, this.name.value, this.mailAddress.value
    )