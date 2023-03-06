package com.example.peachapi.domain.status

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.category.CategoryId
import com.google.protobuf.Api

interface StatusRepository {
    fun getByCategoryId(categoryId: CategoryId): Either<ApiException, Statuses>
    fun create(status: Status): Either<ApiException, Status>
}