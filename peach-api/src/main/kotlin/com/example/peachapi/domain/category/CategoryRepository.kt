package com.example.peachapi.domain.category

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.user.UserId

interface CategoryRepository {
    fun create(category: Category): Either<ApiException, Category>
    fun getCategories(groupId: GroupId, userId: UserId): Either<ApiException, Categories>
}