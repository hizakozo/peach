package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.ItemId
import com.example.peachapi.domain.status.Status
import com.example.peachapi.domain.status.StatusColor
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.status.StatusName
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.gen.Tables.DELETE_STATUS
import com.example.peachapi.driver.peachdb.gen.Tables.STATUES
import com.example.peachapi.driver.peachdb.gen.tables.records.StatuesRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StatusDriver(private val dsl: DSLContext) {

    suspend fun fetchByCategoryId(categoryId: CategoryId): Either<Throwable, List<StatusRecord>> =
        Either.catch {
            dsl.select(STATUES.STATUS_ID, STATUES.CATEGORY_ID, STATUES.STATUS_NAME, STATUES.STATUS_COLOR, STATUES.CREATED_BY, STATUES.CHANGED_BY)
                .from(STATUES)
                .where(STATUES.CATEGORY_ID.eq(categoryId.value))
                .fetchInto(StatusRecord::class.java)
        }

    suspend fun create(status: Status): Either<Throwable, StatusRecord?> =
        Either.catch {
            val query = dsl.insertInto(STATUES)
                .set(STATUES.STATUS_ID, status.statusId.value)
                .set(STATUES.CATEGORY_ID, status.categoryId.value)
                .set(STATUES.STATUS_NAME, status.statusName.value)
                .set(STATUES.CREATED_BY, status.createBy.value)
                .set(STATUES.CHANGED_BY, status.changedBy.value)
            if (status.statusColor.value.isNotEmpty()) {
                query.set(STATUES.STATUS_COLOR, status.statusColor.value)
            }
            query.returning().fetchOne()?.toRecord()
        }

    suspend fun update(statusId: StatusId, statusName: StatusName, statusColor: StatusColor, changedBy: UserId): Either<Throwable, StatusRecord?> =
        Either.catch {
            dsl.update(STATUES)
                .set(STATUES.STATUS_NAME, statusName.value)
                .set(STATUES.CHANGED_BY, changedBy.value)
                .set(STATUES.STATUS_COLOR, statusColor.value)
                .where(STATUES.STATUS_ID.eq(statusId.value))
                .returning().fetchOne()?.toRecord()
        }
    fun delete(statusId: StatusId, deleteBy: UserId, context: DSLContext): Either<Throwable, UUID?> =
        Either.catch {
            context.insertInto(DELETE_STATUS)
                .set(DELETE_STATUS.STATUS_ID, statusId.value)
                .set(DELETE_STATUS.DELETED_BY, deleteBy.value)
                .returning().fetchOne()?.statusId
        }
}

fun StatuesRecord.toRecord() =
    StatusRecord(
        this.statusId.toString(),
        this.categoryId.toString(),
        this.statusName,
        this.statusColor,
        this.createdBy,
        this.changedBy
    )
data class StatusRecord(
    val statusId: String,
    val categoryId: String,
    val statusName: String,
    val statusColor: String,
    val createdBy: String,
    val changedBy: String
)

data class ItemStatusesRecord(
    val itemId: String,
    val statusId: String
)