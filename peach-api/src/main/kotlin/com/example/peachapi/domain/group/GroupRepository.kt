package com.example.peachapi.domain.group

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.user.UserId

interface GroupRepository {
    fun create(group: Group): Either<ApiException, Unit>
    fun getGroups(userId: UserId): Either<ApiException, Groups>
    fun existsUserGroup(userId: UserId, groupId: GroupId): Either<ApiException, Boolean>
}