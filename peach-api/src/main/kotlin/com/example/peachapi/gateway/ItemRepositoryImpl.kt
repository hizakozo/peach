package com.example.peachapi.gateway

import arrow.core.Either
import arrow.core.computations.ResultEffect.bind
import arrow.core.computations.either
import arrow.core.flatMap
import com.example.peachapi.domain.PeachDateTime
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.*
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.ItemDriver
import com.example.peachapi.driver.peachdb.ItemRecord
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemRepositoryImpl(private val driver: ItemDriver, private val dslContext: DSLContext) : ItemRepository {
    override fun getByCategoryId(categoryId: CategoryId): Either<UnExpectError, Items> =
        driver.fetchByCategoryId(categoryId)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it.toItems() }

    override fun createItem(item: Item): Either<UnExpectError, Item> =
        driver.createItem(item)
            .mapLeft { UnExpectError(it, it.message) }
            .map { item }

    override fun createAssignStatus(
        itemId: ItemId,
        statusId: StatusId,
        assignedBy: UserId
    ): Either<UnExpectError, Item> =
        dslContext.transactionResult { config ->
            val context = DSL.using(config)
            driver.deleteAssignedStatus(itemId, context)
                .map {
                    driver.createAssignStatus(itemId, statusId, assignedBy, context)
                }.toUnExpectError()
                .map { driver.fetchById(itemId).bind()!!.toItem() }
        }

    override suspend fun deleteAssignStatus(itemId: ItemId): Either<UnExpectError, Item> =
        dslContext.transactionResult { config ->
            val context = DSL.using(config)
            driver.deleteAssignedStatus(itemId, context).toUnExpectError()
                .map { driver.fetchById(itemId).bind()!!.toItem() }
        }

    override suspend fun existByUserId(userId: UserId, itemId: ItemId): Either<UnExpectError, Boolean> =
        driver.existByUserId(userId, itemId).toUnExpectError()

    override suspend fun update(
        itemId: ItemId,
        itemName: ItemName,
        itemRemarks: ItemRemarks,
        changedBy: UserId
    ): Either<UnExpectError, ItemId> =
        driver.update(itemId, itemName, itemRemarks, changedBy)
            .toUnExpectError()
            .map { ItemId(it!!) }

    override suspend fun delete(itemId: ItemId, userId: UserId): Either<UnExpectError, ItemId> =
        dslContext.transactionResult { config ->
            val context = DSL.using(config)
            driver.deleteAssignedStatus(itemId, context)
                .flatMap {
                    driver.delete(itemId, context)
                }
                .toUnExpectError()
                .map { itemId }
        }

    override suspend fun getById(itemId: ItemId): Either<UnExpectError, Item?> =
        driver.fetchById(itemId)
            .toUnExpectError()
            .map { it?.toItem() }

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