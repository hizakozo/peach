package com.example.peachapi.controller

import arrow.core.computations.either
import com.example.peachapi.config.AuthenticatedUser
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.group.*
import com.example.peachapi.usecase.GroupUseCase
import com.example.peachapi.usecase.UpdateGroupCommand
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
class GroupsController(private val groupUseCase: GroupUseCase) {
    private suspend fun userContext() = ReactiveSecurityContextHolder.getContext()
        .asFlow().single()

    suspend fun create(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            request.bodyToMono<CreateGroupRequest>().awaitSingle().let { request ->
                either<ApiException, Group> {
                    val group = Group.newGroup(
                        request.groupName,
                        request.groupRemarks,
                        user.userId
                    )
                    groupUseCase.create(group).bind()
                }.fold(
                    { it.toResponse() },
                    { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
                )
            }
        }

    suspend fun getGroups(request: ServerRequest): ServerResponse =
        userContext().let { u ->
            val user = u.authentication.details as AuthenticatedUser
            either<ApiException, Groups> {
                groupUseCase.getGroups(user.userId).bind()
            }.fold(
                { it.toResponse() },
                { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
            )
        }

    suspend fun getCategories(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val groupId = GroupId(UUID.fromString(request.pathVariable("groupId")))
            groupUseCase.getCategories(groupId, user.userId)
                .fold(
                    { it.toResponse() },
                    { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
                )
        }

    suspend fun update(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            request.bodyToMono<CreateGroupRequest>().awaitSingle().let { r ->
                val groupId = GroupId(UUID.fromString(request.pathVariable("groupId")))
                either<ApiException, Group> {
                    val command = UpdateGroupCommand(
                        groupId,
                        GroupName(r.groupName),
                        GroupRemarks(r.groupRemarks ?: ""),
                        user.userId
                    )
                    groupUseCase.update(command).bind()
                }.fold(
                    { it.toResponse() },
                    { ServerResponse.ok().bodyValueAndAwait(it.toResponse()) }
                )
            }
        }

    suspend fun delete(request: ServerRequest): ServerResponse =
        userContext().let { context ->
            val user = context.authentication.details as AuthenticatedUser
            val groupId = GroupId(UUID.fromString(request.pathVariable("groupId")))
            either<ApiException, GroupId> {
                groupUseCase.delete(user.userId, groupId).bind()
            }.fold(
                { it.toResponse() },
                { ServerResponse.ok().bodyValueAndAwait(GroupIdResponse(it.value)) }
            )
        }
}

fun Group.toResponse(): GroupResponse =
    GroupResponse(this.groupId.value, this.groupName.value, this.groupRemarks?.value)

data class CreateGroupRequest(
    val groupName: String,
    val groupRemarks: String?
)

data class GroupResponse(
    val groupId: UUID,
    val groupName: String,
    val groupRemarks: String?
)

data class GroupIdResponse(val groupId: UUID)

fun Groups.toResponse(): GroupsResponse =
    GroupsResponse(
        this.map {
            GroupResponse(
                it.groupId.value, it.groupName.value, it.groupRemarks?.value
            )
        }
    )

data class GroupsResponse(
    val groups: List<GroupResponse>
)

data class CategoriesResponse(
    val categories: List<CategoryResponse>
)

fun Categories.toResponse() =
    CategoriesResponse(
        this.map {
            CategoryResponse(
                it.categoryId.value,
                it.categoryName.value,
                it.categoryRemarks.value
            )
        }
    )