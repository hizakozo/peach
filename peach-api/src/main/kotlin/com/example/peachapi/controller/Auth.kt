package com.example.peachapi.controller

import com.example.peachapi.usecase.UserUseCase
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class Auth(private val useCase: UserUseCase) {

    suspend fun auth(request: ServerRequest): ServerResponse =
        request.bodyToMono<IdTokenRequest>().awaitSingle().let {
            useCase.login(it.idToken)
                .fold(
                    {ServerResponse.badRequest().bodyValueAndAwait(it)},
                    { it -> ServerResponse.ok().bodyValueAndAwait(it)}
                )
        }
}

data class IdTokenRequest(
    val idToken: String
)