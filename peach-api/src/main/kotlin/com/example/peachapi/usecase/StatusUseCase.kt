package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.status.*
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class StatusUseCase(
    private val statusRepository: StatusRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun create(userId: UserId, status: Status): Either<ApiException, Status> =
        categoryRepository.existsByUserID(userId, status.categoryId)
            .flatMap {
                if (it) {
                    statusRepository.create(status)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }

    suspend fun update(c: StatusUpdateCommand): Either<ApiException, Status> =
        categoryRepository.existByStatusId(c.changedBy, c.statusId)
            .flatMap {
                if (it) {
                    statusRepository.update(c.statusId, c.statusName, c.statusColor, c.changedBy)
                } else {
                    Either.Left(PermissionException(null, "unavailable status"))
                }
            }

    suspend fun delete(statusId: StatusId, userId: UserId): Either<ApiException, StatusId> =
        categoryRepository.existByStatusId(userId, statusId)
            .flatMap {
                if (it) {
                    statusRepository.delete(statusId, userId)
                } else {
                    Either.Left(PermissionException(null, "unavailable status"))
                }
            }
}

data class StatusUpdateCommand(
    val statusId: StatusId,
    val statusName: StatusName,
    val statusColor: StatusColor,
    val changedBy: UserId,
)