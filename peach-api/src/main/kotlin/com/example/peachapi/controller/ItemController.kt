package com.example.peachapi.controller

import com.example.peachapi.config.AuthenticatedUser
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.item.Item
import com.example.peachapi.domain.item.Items
import com.example.peachapi.domain.user.UserId
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
import java.util.UUID
import javax.swing.GroupLayout.Group

@Component
class ItemController(private val useCase: ItemUseCase) {
    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

    suspend fun getItems(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val categoryId = CategoryId(UUID.fromString(request.pathVariable("categoryId")))
            val groupId = GroupId(UUID.fromString(request.pathVariable("groupId")))
            useCase.getByCategoryId(categoryId, user.userId, groupId)
                .fold(
                    { it.toResponse() },
                    {
                        ServerResponse
                            .ok()
                            .bodyValueAndAwait(it.toResponse())
                    }
                )
        }

    suspend fun createItem(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            request.bodyToMono<CreateItemRequest>().awaitSingle().let {requestBody ->
                val user = context.authentication.details as AuthenticatedUser
                val categoryId = CategoryId(UUID.fromString(request.pathVariable("categoryId")))
                val groupId = GroupId(UUID.fromString(request.pathVariable("groupId")))
                val item = Item.newItem(
                    categoryId,
                    requestBody.itemName,
                    requestBody.itemRemarks,
                    user.userId
                )
                useCase.createItem(user.userId, groupId, item)
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
    val itemName: String,
    val itemRemarks: String
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
        this.itemRemarks?.value
    )
data class ItemsResponse(
    val items: List<ItemResponse>
)

data class ItemResponse(
    val itemId: String,
    val statusId: String?,
    val itemName: String,
    val itemRemarks: String?
)