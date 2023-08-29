package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.item.Item
import com.example.peachapi.domain.item.ItemId
import com.example.peachapi.domain.item.ItemName
import com.example.peachapi.domain.item.ItemRemarks
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component
import com.example.peachapi.driver.peachdb.gen.Tables.*
import com.example.peachapi.driver.peachdb.gen.tables.Categories
import com.example.peachapi.driver.peachdb.gen.tables.Groups
import com.example.peachapi.driver.peachdb.gen.tables.UserGroups
import com.example.peachapi.driver.peachdb.gen.tables.records.ItemsRecord
import org.jooq.*
import org.jooq.impl.DSL.lateral
import org.jooq.impl.DSL.select
import org.jooq.impl.SQLDataType
import org.springframework.cglib.core.Local
import java.time.LocalDateTime
import java.util.*

@Component
class ItemDriver(private val dsl: DSLContext) {
    private fun selectBase(): SelectOnConditionStep<Record8<UUID, UUID, UUID, String, String, String, String, LocalDateTime>> {
        return dsl.select(ITEMS.ITEM_ID, ITEMS.CATEGORY_ID, ASSIGNED_STATUS.STATUS_ID, ITEMS.ITEM_NAME, ITEMS.ITEM_REMARKS, ITEMS.CREATED_BY, ITEMS.CHANGED_BY, ITEMS.CREATED_AT)
            .from(ITEMS)
            .leftOuterJoin(ASSIGNED_STATUS)
            .on(ASSIGNED_STATUS.ITEM_ID.eq(ITEMS.ITEM_ID))
    }
    fun fetchByCategoryId(categoryId: CategoryId): Either<Throwable, List<ItemRecord>> =
        Either.catch {
            selectBase()
                .where(ITEMS.CATEGORY_ID.eq(categoryId.value))
                .fetchInto(ItemRecord::class.java)
        }

    fun fetchById(itemId: ItemId): Either<Throwable, ItemRecord?> =
        Either.catch {
            selectBase()
                .where(ITEMS.ITEM_ID.eq(itemId.value))
                .fetchOneInto(ItemRecord::class.java)
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
    fun createAssignStatus(itemId: ItemId, statusId: StatusId, assignedBy: UserId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.insertInto(ASSIGNED_STATUS)
                .set(ASSIGNED_STATUS.ITEM_ID, itemId.value)
                .set(ASSIGNED_STATUS.STATUS_ID, statusId.value)
                .set(ASSIGNED_STATUS.ASSIGNED_BY, assignedBy.value)
                .execute()
        }
    fun deleteAssignedStatus(itemId: ItemId, context: DSLContext): Either<Throwable, UUID?> =
        Either.catch {
            context.delete(ASSIGNED_STATUS)
                .where(ASSIGNED_STATUS.ITEM_ID.eq(itemId.value))
                .returning().fetchOne()?.itemId
        }
    fun deleteAssignedStatusByStatusId(statusId: StatusId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.delete(ASSIGNED_STATUS)
                .where(ASSIGNED_STATUS.STATUS_ID.eq(statusId.value))
                .execute()
        }
    fun existByUserId(userId: UserId, itemId: ItemId): Either<Throwable, Boolean> =
        Either.catch {
            dsl.fetchExists(
                dsl.selectOne()
                    .from(ITEMS)
                    .innerJoin(CATEGORIES).on(ITEMS.CATEGORY_ID.eq(CATEGORIES.CATEGORY_ID))
                    .innerJoin(GROUPS).on(CATEGORIES.GROUP_ID.eq(GROUPS.GROUP_ID))
                    .innerJoin(USER_GROUPS).on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                    .where(USER_GROUPS.USER_ID.eq(userId.value).and(ITEMS.ITEM_ID.eq(itemId.value)))
            )
        }
    suspend fun update(itemId: ItemId, itemName: ItemName, itemRemarks: ItemRemarks, changedBy: UserId): Either<Throwable, UUID?> =
        Either.catch {
            dsl.update(ITEMS)
                .set(ITEMS.ITEM_NAME, itemName.value)
                .set(ITEMS.ITEM_REMARKS, itemRemarks.value)
                .set(ITEMS.CHANGED_BY, changedBy.value)
                .where(ITEMS.ITEM_ID.eq(itemId.value))
                .returning().fetchOne()?.itemId
        }
    fun insertDeleteItem(itemId: ItemId, deleteBy: UserId, context: DSLContext): Either<Throwable, UUID?> =
        Either.catch {
            context.insertInto(DELETE_ITEM)
                .set(DELETE_ITEM.ITEM_ID, itemId.value)
                .set(DELETE_ITEM.DELETED_BY, deleteBy.value)
                .returning().fetchOne()?.itemId
        }
    fun delete(itemId: ItemId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.delete(ITEMS).where(ITEMS.ITEM_ID.eq(itemId.value)).execute()
        }
}

data class ItemRecord(
    val itemId: String,
    val categoryId: String,
    val statusId: String?,
    val itemName: String,
    val itemRemarks: String,
    val createdBy: String,
    val changedBy: String,
    val createdAt: LocalDateTime
)