package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.Group
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.group.Groups
import com.example.peachapi.domain.user.UserId
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
}