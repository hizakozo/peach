package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.status.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.ItemDriver
import com.example.peachapi.driver.peachdb.StatusDriver
import com.example.peachapi.driver.peachdb.StatusRecord
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.util.*

@Component
class StatusRepositoryImpl(private val driver: StatusDriver, private val itemDriver: ItemDriver, private val dslContext: DSLContext): StatusRepository {
    override suspend fun getByCategoryId(categoryId: CategoryId): Either<UnExpectError, Statuses> =
        driver.fetchByCategoryId(categoryId)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it.toStatuses() }

    override suspend fun create(status: Status): Either<UnExpectError, Status> =
        driver.create(status)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it?.toStatus() ?: status }

    override suspend fun update(statusId: StatusId, statusName: StatusName, statusColor: StatusColor, changedBy: UserId): Either<UnExpectError, Status> =
        driver.update(statusId, statusName, statusColor, changedBy)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it!!.toStatus() }

    override fun delete(statusId: StatusId, userId: UserId): Either<UnExpectError, StatusId> =
        dslContext.transactionResult { config ->
            val context = DSL.using(config)
            driver.delete(statusId, userId, context)
                .map { itemDriver.deleteAssignedStatusByStatusId(statusId, context) }
                .toUnExpectError()
                .map { statusId }
        }

    private fun List<StatusRecord>.toStatuses() =
        Statuses(
            this.map {
                Status(
                    StatusId(UUID.fromString(it.statusId)),
                    CategoryId(UUID.fromString(it.categoryId)),
                    StatusName(it.statusName),
                    StatusColor(it.statusColor),
                    UserId(it.createdBy),
                    UserId(it.changedBy)
                )
            }
        )
    private fun StatusRecord.toStatus() =
        Status(
            StatusId(UUID.fromString(this.statusId)),
            CategoryId(UUID.fromString(this.categoryId)),
            StatusName(this.statusName),
            StatusColor(this.statusColor),
            UserId(this.createdBy),
            UserId(this.changedBy)
        )
}