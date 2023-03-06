package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.Category
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.item.ItemRepository
import com.example.peachapi.domain.item.Items
import com.example.peachapi.domain.status.StatusRepository
import com.example.peachapi.domain.status.Statuses
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class CategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val groupRepository: GroupRepository,
    private val itemRepository: ItemRepository,
    private val statusRepository: StatusRepository
) {

    suspend fun create(category: Category, userId: UserId): Either<ApiException, Category> =
        groupRepository.existsUserGroup(userId, category.groupId)
            .flatMap { isExist ->
                if (isExist) {
                    categoryRepository.create(category)
                } else {
                    Either.Left(PermissionException(null, "unavailable group"))
                }
            }

    fun getItems(categoryId: CategoryId, userId: UserId): Either<ApiException, Items> =
        categoryRepository.existsByUserID(userId, categoryId)
            .flatMap { isExist ->
                if (isExist) {
                    itemRepository.getByCategoryId(categoryId)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }

    fun getStatues(userId: UserId, categoryId: CategoryId): Either<ApiException, Statuses> =
        categoryRepository.existsByUserID(userId, categoryId)
            .flatMap {
                if (it) {
                    statusRepository.getByCategoryId(categoryId)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
}