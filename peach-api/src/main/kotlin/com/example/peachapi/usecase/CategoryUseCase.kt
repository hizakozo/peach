package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.*
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.item.ItemRepository
import com.example.peachapi.domain.item.Items
import com.example.peachapi.domain.status.StatusRepository
import com.example.peachapi.domain.status.Statuses
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api
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

    suspend fun getItems(categoryId: CategoryId, userId: UserId): Either<ApiException, Items> =
        categoryRepository.existsByUserID(userId, categoryId)
            .flatMap { isExist ->
                if (isExist) {
                    itemRepository.getByCategoryId(categoryId)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }

    suspend fun getStatues(userId: UserId, categoryId: CategoryId): Either<ApiException, Statuses> =
        categoryRepository.existsByUserID(userId, categoryId)
            .flatMap {
                if (it) {
                    statusRepository.getByCategoryId(categoryId)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
    suspend fun update(command: UpdateCommand): Either<ApiException, Category> =
        categoryRepository.existsByUserID(command.changedBy, command.categoryId)
            .flatMap {
                if (it) {
                    categoryRepository.update(command.categoryId, command.categoryName, command.categoryRemarks, command.changedBy)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
    suspend fun delete(command: DeleteCommand): Either<ApiException, CategoryId> =
        categoryRepository.existsByUserID(command.deletedBy, command.categoryId)
            .flatMap {
                if (it) {
                    categoryRepository.delete(command.categoryId, command.deletedBy)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
}

data class UpdateCommand(
    val categoryId: CategoryId,
    val categoryName: CategoryName,
    val categoryRemarks: CategoryRemarks,
    val changedBy: UserId
)

data class DeleteCommand(
    val deletedBy: UserId,
    val categoryId: CategoryId
)