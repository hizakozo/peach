package com.example.peachapi.gateway

import arrow.core.Either
import arrow.core.computations.ResultEffect.bind
import arrow.core.computations.either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PeachDateTime
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.*
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.ItemDriver
import com.example.peachapi.driver.peachdb.ItemRecord
import com.google.firebase.internal.ApiClientUtils
import org.jooq.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemRepositoryImpl(private val driver: ItemDriver): ItemRepository {
    override fun getByCategoryId(categoryId: CategoryId): Either<ApiException, Items> =
        driver.fetchByCategoryId(categoryId)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it.toItems() }

    override fun createItem(item: Item): Either<ApiException, Item> =
        driver.createItem(item)
            .mapLeft { UnExpectError(it, it.message) }
            .map { item }
    override fun createAssignStatus(
        itemId: ItemId,
        statusId: StatusId,
        assignedBy: UserId
    ): Either<ApiException, Item> =
            driver.createAssignStatus(itemId, statusId, assignedBy)
                .mapLeft { UnExpectError(it, it.message) }
                .map {
                    driver.fetchById(itemId).bind()!!.toItem()
                }

    override suspend fun existByUserId(userId: UserId, itemId: ItemId): Either<ApiException, Boolean> =
        driver.existByUserId(userId, itemId)
            .mapLeft { UnExpectError(it, it.message) }

    private fun List<ItemRecord>.toItems(): Items =
        Items(
            this.map { it.toItem() }
        )

    private fun ItemRecord.toItem(): Item =
        Item(
            ItemId(UUID.fromString(this.itemId)),
            CategoryId(UUID.fromString(this.categoryId)),
            if (this.statusId != null) StatusId(UUID.fromString(this.statusId)) else null,
            ItemName(this.itemName),
            ItemRemarks(this.itemRemarks),
            UserId(this.createdBy),
            UserId(this.changedBy),
            PeachDateTime(this.createdAt)
        )
}