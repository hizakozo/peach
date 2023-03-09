package com.example.peachapi.domain.status

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api

interface StatusRepository {
    suspend fun getByCategoryId(categoryId: CategoryId): Either<UnExpectError, Statuses>
    suspend fun create(status: Status): Either<UnExpectError, Status>
    suspend fun update(statusId: StatusId, statusName: StatusName, statusColor: StatusColor, changedBy: UserId): Either<UnExpectError, Status>
    fun delete(statusId: StatusId, userId: UserId): Either<UnExpectError, StatusId>
}