package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.fx.coroutines.parZip
import com.example.peachapi.domain.*
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName
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
            .flatMap { isExist ->
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
                if (!it) {
                    Either.Left(PermissionException(null, "unavailable group"))
                } else {
                    groupRepository.delete(groupId, userId)
                }
            }

    suspend fun inviteGroup(
        groupInviteCode: GroupInviteCode,
        userId: UserId
    ): Either<ApiException, Pair<GroupInviteCode, UserName>> =
        either {
            parZip(
                { groupRepository.existsUserGroup(userId, groupInviteCode.groupId).bind() },
                { groupRepository.existsInviteCode(groupInviteCode.inviteCode).bind() },
            ) { existUserGroup, existInviteCode ->
                if (!existUserGroup) {
                    Either.Left(PermissionException(null, "unavailable group"))
                } else if (existInviteCode) {
                    Either.Left(BadRequestException(null, "exist inviteCode"))
                } else {
                    val groupInviteInfo = groupRepository.createInviteCode(groupInviteCode, userId).bind()
                    Either.Right(groupInviteInfo)
                }
            }.bind()
        }

    suspend fun detail(groupId: GroupId, userId: UserId): Either<ApiException, Pair<Group, UserGroups>> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {
                if (it) {
                    groupRepository.getGroup(groupId).flatMap { pair ->
                        if (pair.first == null) {
                            Either.Left(NotFoundDataException(null, "not fond data group"))
                        } else {
                            Either.Right(Pair(pair.first!!, pair.second))
                        }
                    }
                } else {
                    Either.Left(PermissionException(null, "unavailable group"))
                }
            }

    suspend fun joinGroup(userId: UserId, groupInviteCode: GroupInviteCode): Either<ApiException, GroupId> =
        groupRepository.fetchGroupInviteCode(groupInviteCode.groupId)
            .flatMap {
                if (it == null) Either.Left(NotFoundDataException(null, "not found invite code"))
                else if (!it.first.inviteCode.isSame(groupInviteCode.inviteCode)) Either.Left(
                    BadRequestException(
                        null,
                        "Invalid code"
                    )
                )
                else if (!it.first.isTermOfValidity()) Either.Left(BadRequestException(null, "expiration of a term"))
                else groupRepository.createUserGroups(userId, groupInviteCode.groupId).flatMap { Either.Right(it.second) }
            }

}

data class UpdateGroupCommand(
    val groupId: GroupId,
    val groupName: GroupName,
    val groupRemarks: GroupRemarks,
    val changedBy: UserId
)