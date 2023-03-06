package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.Item
import com.example.peachapi.driver.peachdb.gen.tables.ItemStatues
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import com.example.peachapi.driver.peachdb.gen.Tables.*

@Component
class ItemDriver(private val dsl: DSLContext) {

    fun fetchByCategoryId(categoryId: CategoryId): Either<Throwable, List<ItemRecord>> =
        Either.catch {
            dsl.select(ITEMS.ITEM_ID, ITEMS.CATEGORY_ID, ITEM_STATUES.STATUS_ID, ITEMS.ITEM_NAME, ITEMS.ITEM_REMARKS, ITEMS.CREATED_BY, ITEMS.CHANGED_BY)
                .from(ITEMS)
                .leftOuterJoin(ITEM_STATUES).on(ITEMS.ITEM_ID.eq(ITEM_STATUES.ITEM_ID))
                .where(ITEMS.CATEGORY_ID.eq(categoryId.value))
                .fetchInto(ItemRecord::class.java)
        }
    fun createItem(item: Item): Either<Throwable, Unit> =
        Either.catch {
            dsl.insertInto(ITEMS)
                .set(ITEMS.ITEM_ID, item.itemId.value)
                .set(ITEMS.CATEGORY_ID, item.categoryId.value)
                .set(ITEMS.ITEM_NAME, item.itemName.value)
                .set(ITEMS.ITEM_REMARKS, item.itemRemarks?.value)
                .set(ITEMS.CREATED_BY, item.createBy.value)
                .set(ITEMS.CHANGED_BY, item.changedBy.value)
                .execute()
        }
}

data class ItemRecord(
    val itemId: String,
    val categoryId: String,
    val statusId: String?,
    val itemName: String,
    val itemRemarks: String,
    val createdBy: String,
    val changedBy: String
)