package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.ItemId
import com.example.peachapi.domain.status.Status
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.gen.Tables.ASSIGN_STATUS
import com.example.peachapi.driver.peachdb.gen.Tables.STATUES
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class StatusDriver(private val dsl: DSLContext) {

    fun fetchByCategoryId(categoryId: CategoryId): Either<Throwable, List<StatusRecord>> =
        Either.catch {
            dsl.select(STATUES.STATUS_ID, STATUES.CATEGORY_ID, STATUES.STATUS_NAME, STATUES.STATUS_COLOR, STATUES.CREATED_BY, STATUES.CHANGED_BY)
                .from(STATUES)
                .where(STATUES.CATEGORY_ID.eq(categoryId.value))
                .fetchInto(StatusRecord::class.java)
        }

    fun create(status: Status): Either<Throwable, Unit> =
        Either.catch {
            dsl.insertInto(STATUES)
                .set(STATUES.STATUS_ID, status.statusId.value)
                .set(STATUES.CATEGORY_ID, status.categoryId.value)
                .set(STATUES.STATUS_NAME, status.statusName.value)
                .set(STATUES.STATUS_COLOR, status.statusColor.value)
                .set(STATUES.CREATED_BY, status.createBy.value)
                .set(STATUES.CREATED_BY, status.changedBy.value)
                .execute()
        }
}

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