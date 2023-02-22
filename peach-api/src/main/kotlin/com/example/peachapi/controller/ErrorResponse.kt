package com.example.peachapi.controller

import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.UnExpectError
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

suspend fun ApiException.toResponse(): ServerResponse =
    when(this) {
        is UnExpectError -> {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValueAndAwait(this.message?: "un expected error")
        }
        is PermissionException -> {
            ServerResponse.status(HttpStatus.FORBIDDEN)
                .bodyValueAndAwait(this.message?: "permission error")
        }
        else -> {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValueAndAwait("")
        }
    }