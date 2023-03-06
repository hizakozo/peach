package com.example.peachapi.controller

import com.example.peachapi.config.AuthenticatedUser
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.status.Status
import com.example.peachapi.domain.status.Statuses
import com.example.peachapi.usecase.StatusUseCase
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.*

@Component
class StatusController(private val statusUseCase: StatusUseCase) {
    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

    suspend fun create(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            request.bodyToMono<CreateStatusRequest>().awaitSingle().let { requestBody ->
                val status = Status.newStatus(
                    CategoryId(UUID.fromString(requestBody.categoryId)),
                    requestBody.statusName,
                    requestBody.statusColor,
                    user.userId
                )
                statusUseCase.create(user.userId, status)
                    .fold(
                        { it.toResponse() },
                        {
                            ServerResponse
                                .ok()
                                .bodyValueAndAwait(it.toResponse())
                        }
                    )
            }
        }
}

data class CreateStatusRequest(
    val categoryId: String,
    val statusName: String,
    val statusColor: String
)

data class StatusesResponse(
    val statuses: List<StatusResponse>
)
fun Statuses.toResponse() =
    StatusesResponse(
        this.list.map {
            it.toResponse()
        }
    )
fun Status.toResponse() =
    StatusResponse(
        this.statusId.value.toString(),
        this.statusName.value,
        this.statusColor.value
    )

data class StatusResponse(
    val statusId: String,
    val statusName: String,
    val statusColor: String
)