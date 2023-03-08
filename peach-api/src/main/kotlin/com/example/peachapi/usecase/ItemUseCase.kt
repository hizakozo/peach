package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.fx.coroutines.parZip
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.item.Item
import com.example.peachapi.domain.item.ItemId
import com.example.peachapi.domain.item.ItemRepository
import com.example.peachapi.domain.item.Items
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
            parZip (
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
}