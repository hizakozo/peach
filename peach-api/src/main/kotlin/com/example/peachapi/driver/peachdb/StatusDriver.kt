package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.ItemId
import com.example.peachapi.domain.item.Items
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import com.example.peachapi.driver.peachdb.gen.Tables.*
@Component
class StatusDriver(private val dsl: DSLContext) {

    fun fetchByCategoryId(categoryId: CategoryId): Either<Throwable, List<StatusRecord>> =
        Either.catch {
            dsl.select(STATUES.STATUS_ID, STATUES.CATEGORY_ID, STATUES.STATUS_NAME, STATUES.STATUS_COLOR)
                .from(STATUES)
                .where(STATUES.CATEGORY_ID.eq(categoryId.value))
                .fetchInto(StatusRecord::class.java)
        }

    fun fetchItemStatuesByItemId(itemId: ItemId): Either<Throwable, List<ItemStatusesRecord>> =
        Either.catch {
            dsl.select(ITEM_STATUES.ITEM_ID, ITEM_STATUES.STATUS_ID)
                .from(ITEM_STATUES)
                .where(ITEM_STATUES.ITEM_ID.eq(itemId.value))
                .fetchInto(ItemStatusesRecord::class.java)
        }
}

data class StatusRecord(
    val statusId: String,
    val categoryId: String,
    val statusName: String,
    val statusColor: String
)

data class ItemStatusesRecord(
    val itemId: String,
    val statusId: String
)