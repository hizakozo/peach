package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.status.Status
import com.example.peachapi.domain.status.StatusRepository
import com.example.peachapi.domain.status.Statuses
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class StatusUseCase(private val statusRepository: StatusRepository, private val groupRepository: GroupRepository) {

    fun create(userId: UserId, groupId: GroupId, status: Status): Either<ApiException, Status> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {
                if (it) {
                    statusRepository.create(status)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
    fun byCategoryId(userId: UserId, groupId: GroupId, categoryId: CategoryId): Either<ApiException, Statuses> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {
                if (it) {
                    statusRepository.getByCategoryId(categoryId)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
}