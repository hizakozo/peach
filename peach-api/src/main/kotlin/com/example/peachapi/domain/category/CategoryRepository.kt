package com.example.peachapi.domain.category

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api

interface CategoryRepository {
    fun create(category: Category): Either<UnExpectError, Category>
    fun getCategories(groupId: GroupId, userId: UserId): Either<UnExpectError, Categories>
    suspend  fun existsByUserID(userId: UserId, categoryId: CategoryId): Either<UnExpectError, Boolean>
    suspend fun existByStatusId(userId: UserId, statusId: StatusId): Either<UnExpectError, Boolean>
    suspend fun delete(categoryId: CategoryId, userId: UserId): Either<UnExpectError, CategoryId>
    suspend fun update(categoryId: CategoryId, categoryName: CategoryName, categoryRemarks: CategoryRemarks, changedBy: UserId): Either<UnExpectError, Category>
}