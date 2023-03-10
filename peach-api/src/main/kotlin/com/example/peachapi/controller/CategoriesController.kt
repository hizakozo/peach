package com.example.peachapi.controller

import com.example.peachapi.config.AuthenticatedUser
import com.example.peachapi.domain.category.*
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.usecase.CategoryUseCase
import com.example.peachapi.usecase.DeleteCommand
import com.example.peachapi.usecase.UpdateCommand
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

@Component
class CategoriesController(private val categoryUseCase: CategoryUseCase) {
    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

    suspend fun create(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            request.bodyToMono<CreateCategoryRequest>().awaitSingle().let { request ->
                val category = Category.newCategory(
                    request.categoryName,
                    GroupId(UUID.fromString(request.groupId)),
                    request.categoryRemarks,
                    user.userId
                )
                categoryUseCase.create(category, user.userId).fold(
                    { it.toResponse() },
                    { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
                )
            }
        }

    suspend fun getItems(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val categoryId = CategoryId(UUID.fromString(request.pathVariable("categoryId")))
            categoryUseCase.getItems(categoryId, user.userId)
                .fold(
                    { it.toResponse() },
                    {
                        ServerResponse
                            .ok()
                            .bodyValueAndAwait(it.toResponse())
                    }
                )
        }

    suspend fun getStatues(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val categoryId = CategoryId(UUID.fromString(request.pathVariable("categoryId")))
            categoryUseCase.getStatues(user.userId, categoryId)
                .fold(
                    { it.toResponse() },
                    {
                        ServerResponse
                            .ok()
                            .bodyValueAndAwait(it.toResponse())
                    }
                )
        }

    suspend fun update(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val categoryId = CategoryId(UUID.fromString(request.pathVariable("categoryId")))
            request.bodyToMono<UpdateCategoryRequest>().awaitSingle().let { r ->
                val command = UpdateCommand(
                    categoryId,
                    CategoryName(r.categoryName),
                    CategoryRemarks(r.categoryRemarks),
                    user.userId
                )
                categoryUseCase.update(command).fold(
                    { it.toResponse() },
                    { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
                )
            }
        }

    suspend fun delete(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val categoryId = CategoryId(UUID.fromString(request.pathVariable("categoryId")))
            val command = DeleteCommand(
                user.userId,
                categoryId
            )
            categoryUseCase.delete(command).fold(
                { it.toResponse() },
                { ServerResponse.ok().bodyValueAndAwait(CategoryIdResponse(it.value)) }
            )
        }
}

data class CreateCategoryRequest(
    val groupId: String,
    val categoryName: String,
    val categoryRemarks: String
)

data class UpdateCategoryRequest(
    val categoryName: String,
    val categoryRemarks: String
)

data class CategoryResponse(
    val categoryId: UUID,
    val categoryName: String,
    val categoryRemarks: String
)

data class CategoryIdResponse(
    val categoryId: UUID
)

fun Category.toResponse() =
    CategoryResponse(
        this.categoryId.value,
        this.categoryName.value,
        this.categoryRemarks.value
    )
