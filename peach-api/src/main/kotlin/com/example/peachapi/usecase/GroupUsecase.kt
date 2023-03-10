package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api
import org.springframework.stereotype.Component

@Component
class GroupUseCase(private val groupRepository: GroupRepository, private val categoryRepository: CategoryRepository) {

    suspend fun create(group: Group): Either<ApiException, Group> =
        groupRepository.create(group)
            .map { group }

    suspend fun getGroups(userId: UserId): Either<ApiException, Groups> =
        groupRepository.getGroups(userId)

    suspend fun getCategories(groupId: GroupId, userId: UserId): Either<ApiException, Categories> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {isExist ->
                if (isExist) {
                    categoryRepository.getCategories(groupId, userId)
                } else {
                    Either.Left(PermissionException(null, "unavailable group"))
                }
            }
    suspend fun update(command: UpdateGroupCommand): Either<ApiException, Group> =
        groupRepository.existsUserGroup(command.changedBy, command.groupId)
            .flatMap {
                if (it) {
                    groupRepository.update(command.groupId, command.groupName, command.groupRemarks, command.changedBy)
                } else {
                    Either.Left(PermissionException(null, "unavailable group"))
                }
            }
    suspend fun delete(userId: UserId, groupId: GroupId): Either<ApiException, GroupId> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {
                if (it) {
                    groupRepository.delete(groupId, userId)
                } else {
                    Either.Left(PermissionException(null, "unavailable group"))
                }
            }
}

data class UpdateGroupCommand(
    val groupId: GroupId,
    val groupName: GroupName,
    val groupRemarks: GroupRemarks,
    val changedBy: UserId
)