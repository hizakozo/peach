package com.example.peachapi.domain.category

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api

interface CategoryRepository {
    fun create(category: Category): Either<ApiException, Category>
    fun getCategories(groupId: GroupId, userId: UserId): Either<ApiException, Categories>
    suspend  fun existsByUserID(userId: UserId, categoryId: CategoryId): Either<ApiException, Boolean>
    suspend fun existByStatusId(userId: UserId, statusId: StatusId): Either<ApiException, Boolean>
}