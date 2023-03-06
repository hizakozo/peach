package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.status.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.StatusDriver
import com.example.peachapi.driver.peachdb.StatusRecord
import org.springframework.stereotype.Component
import java.util.*

@Component
class StatusRepositoryImpl(private val driver: StatusDriver): StatusRepository {
    override fun getByCategoryId(categoryId: CategoryId): Either<ApiException, Statuses> =
        driver.fetchByCategoryId(categoryId)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it.toStatuses() }

    override fun create(status: Status): Either<ApiException, Status> =
        driver.create(status)
            .mapLeft { UnExpectError(it, it.message) }
            .map { status }

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
}