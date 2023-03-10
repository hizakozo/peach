package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.fx.coroutines.parZip
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.NotFoundDataException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.item.*
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api
import org.springframework.stereotype.Component

@Component
class ItemUseCase(private val itemRepository: ItemRepository, private val categoryRepository: CategoryRepository) {

    suspend fun createItem(userId: UserId, item: Item): Either<ApiException, Item> =
        categoryRepository.existsByUserID(userId, item.categoryId)
            .flatMap {
                if (it) {
                    itemRepository.createItem(item)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }

    suspend fun assignStatus(itemId: ItemId, statusId: StatusId, assignedBy: UserId): Either<ApiException, Item> =
        either {
            parZip(
                { itemRepository.existByUserId(assignedBy, itemId).bind() },
                { categoryRepository.existByStatusId(assignedBy, statusId).bind() }
            ) { availableItem, availableCategory ->
                if (availableItem && availableCategory) {
                    itemRepository.createAssignStatus(itemId, statusId, assignedBy)
                } else {
                    Either.Left(PermissionException(null, "unavailable status or item"))
                }.bind()
            }
        }

    suspend fun update(command: UpdateItemCommand): Either<ApiException, Item> =
        itemRepository.existByUserId(command.changedBy, command.itemId)
            .flatMap { isExist ->
                if (isExist) {
                    itemRepository.update(command.itemId, command.itemName, command.itemRemarks, command.changedBy)
                        .flatMap { itemId ->
                            itemRepository.getById(itemId)
                                .flatMap {
                                    if (it == null) Either.Left(NotFoundDataException(null, "not found item"))
                                    else Either.Right(it)
                                }
                        }

                } else {
                    Either.Left(PermissionException(null, "unavailable status or item"))
                }
            }

    suspend fun delete(itemId: ItemId, userId: UserId): Either<ApiException, ItemId> =
        itemRepository.existByUserId(userId, itemId)
            .flatMap {
                if (it) {
                    itemRepository.delete(itemId, userId)
                } else {
                    Either.Left(PermissionException(null, "unavailable status or item"))
                }
            }

    suspend fun unAssignStatus(itemId: ItemId, userId: UserId): Either<ApiException, Item> =
        itemRepository.existByUserId(userId, itemId)
            .flatMap {
                if (it) {
                    itemRepository.deleteAssignStatus(itemId)
                } else {
                    Either.Left(PermissionException(null, "unavailable status or item"))
                }
            }
}

data class UpdateItemCommand(
    val itemId: ItemId,
    val itemName: ItemName,
    val itemRemarks: ItemRemarks,
    val changedBy: UserId
)