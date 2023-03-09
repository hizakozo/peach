package com.example.peachapi.domain.group

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.user.UserId

interface GroupRepository {
    fun create(group: Group): Either<UnExpectError, Unit>
    fun getGroups(userId: UserId): Either<UnExpectError, Groups>
    fun existsUserGroup(userId: UserId, groupId: GroupId): Either<UnExpectError, Boolean>
    suspend fun update(groupId: GroupId, groupName: GroupName, groupRemarks: GroupRemarks, changedBy: UserId): Either<UnExpectError, Group>
    suspend fun delete(groupId: GroupId, userId: UserId): Either<UnExpectError, GroupId>
}