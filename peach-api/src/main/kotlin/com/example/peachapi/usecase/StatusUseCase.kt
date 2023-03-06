package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.status.Status
import com.example.peachapi.domain.status.StatusRepository
import com.example.peachapi.domain.status.Statuses
import com.example.peachapi.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class StatusUseCase(private val statusRepository: StatusRepository, private val categoryRepository: CategoryRepository) {

    fun create(userId: UserId, status: Status): Either<ApiException, Status> =
        categoryRepository.existsByUserID(userId, status.categoryId)
            .flatMap {
                if (it) {
                    statusRepository.create(status)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
}