package com.example.peachapi.config

import kotlinx.coroutines.flow.single
import kotlinx.coroutines.reactive.asFlow
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class GroupController {
    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

//    suspend fun create(request: ServerRequest): ServerResponse =
//        userContext().let {
//            val user = it.authentication.details as AuthenticatedUser
//        }
}