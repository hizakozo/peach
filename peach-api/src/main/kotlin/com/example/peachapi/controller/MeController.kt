package com.example.peachapi.controller

import com.example.peachapi.config.AuthenticatedUser
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
        userContext()
            .let {
                val user = it.authentication.details as AuthenticatedUser
                ServerResponse
                    .ok()
                    .bodyValueAndAwait(user.toResponse())
            }
}

fun AuthenticatedUser.toResponse() =
    MeResponse(
        this.userId.value.toString(),
        this.name.value,
        this.mailAddress.value
    )
data class MeResponse(
    val userId: String,
    val userName: String,
    val mailAddress: String
)