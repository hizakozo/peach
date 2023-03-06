package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.*
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.ItemDriver
import com.example.peachapi.driver.peachdb.ItemRecord
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
    private fun List<ItemRecord>.toItems(): Items =
        Items(
            this.map { i ->
                Item(
                    ItemId(UUID.fromString(i.itemId)),
                    CategoryId(UUID.fromString(i.categoryId)),
                    if (i.statusId != null) StatusId(UUID.fromString(i.statusId)) else null,
                    ItemName(i.itemName),
                    ItemRemarks(i.itemRemarks),
                    UserId(i.createdBy),
                    UserId(i.changedBy)
                )
            }
        )
}