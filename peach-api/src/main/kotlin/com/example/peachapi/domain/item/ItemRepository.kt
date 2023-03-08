package com.example.peachapi.domain.item

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api

interface ItemRepository {
    fun getByCategoryId(categoryId: CategoryId): Either<ApiException, Items>
    fun createItem(item: Item): Either<ApiException, Item>
    fun createAssignStatus(itemId: ItemId, statusId: StatusId, assignedBy: UserId): Either<ApiException, Item>
    suspend fun existByUserId(userId: UserId, itemId: ItemId): Either<ApiException, Boolean>
}