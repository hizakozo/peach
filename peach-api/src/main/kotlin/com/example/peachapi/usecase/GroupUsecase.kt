package com.example.peachapi.usecase

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.group.Group
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.group.Groups
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class GroupUseCase(private val groupRepository: GroupRepository) {

    suspend fun create(group: Group): Either<ApiException, Group> =
        groupRepository.create(group)
            .map { group }

    suspend fun getGroups(userId: UserId): Either<ApiException, Groups> =
        groupRepository.getGroups(userId)
}