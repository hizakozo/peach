package com.example.peachapi.controller

import com.example.peachapi.config.AuthenticatedUser
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.Item
import com.example.peachapi.domain.item.ItemId
import com.example.peachapi.domain.item.Items
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.usecase.ItemUseCase
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
class ItemController(private val useCase: ItemUseCase) {
    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

    suspend fun createItem(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            request.bodyToMono<CreateItemRequest>().awaitSingle().let {requestBody ->
                val user = context.authentication.details as AuthenticatedUser
                val item = Item.newItem(
                    CategoryId(UUID.fromString(requestBody.categoryId)),
                    requestBody.itemName,
                    requestBody.itemRemarks,
                    user.userId
                )
                useCase.createItem(user.userId, item)
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
    suspend fun assignStatus(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            request.bodyToMono<AssignStatusRequest>().awaitSingle().let {requestBody ->
                val user = context.authentication.details as AuthenticatedUser
                val statusId = StatusId(UUID.fromString(requestBody.statusId))
                val itemId = ItemId(UUID.fromString(request.pathVariable("itemId")))
                useCase.assignStatus(itemId, statusId, user.userId)
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

data class CreateItemRequest(
    val categoryId: String,
    val itemName: String,
    val itemRemarks: String
)
data class AssignStatusRequest(
    val statusId: String
)
fun Items.toResponse(): ItemsResponse =
    ItemsResponse(
        this.map { it.toResponse() }
    )

fun  Item.toResponse(): ItemResponse =
    ItemResponse(
        this.itemId.value.toString(),
        if(this.statusId != null) this.statusId.value.toString() else "EMPTY_STATUS",
        this.itemName.value,
        this.itemRemarks?.value,
        this.createdAt.value.toString()
    )
data class ItemsResponse(
    val items: List<ItemResponse>
)

data class ItemResponse(
    val itemId: String,
    val statusId: String?,
    val itemName: String,
    val itemRemarks: String?,
    val createdAt: String
)