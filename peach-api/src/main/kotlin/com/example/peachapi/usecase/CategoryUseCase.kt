package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.Category
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class CategoryUseCase(private val categoryRepository: CategoryRepository, private val groupRepository: GroupRepository) {

    suspend fun create(category: Category, userId: UserId): Either<ApiException, Category> =
        groupRepository.existsUserGroup(userId, category.groupId)
            .flatMap {isExist ->
                if (isExist) {
                    categoryRepository.create(category)
                } else {
                    Either.Left(PermissionException(null, "unavailable group"))
                }
            }
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