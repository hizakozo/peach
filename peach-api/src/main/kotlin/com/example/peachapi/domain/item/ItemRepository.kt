package com.example.peachapi.domain.item

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api

interface ItemRepository {
    fun getByCategoryId(categoryId: CategoryId): Either<UnExpectError, Items>
    fun createItem(item: Item): Either<UnExpectError, Item>
    fun createAssignStatus(itemId: ItemId, statusId: StatusId, assignedBy: UserId): Either<UnExpectError, Item>
    suspend fun existByUserId(userId: UserId, itemId: ItemId): Either<UnExpectError, Boolean>
    suspend fun update(itemId: ItemId, itemName: ItemName, itemRemarks: ItemRemarks, changedBy: UserId): Either<UnExpectError, ItemId>
    suspend fun delete(itemId: ItemId, userId: UserId): Either<UnExpectError, ItemId>
}