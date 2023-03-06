package com.example.peachapi.domain.item

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.category.CategoryId

interface ItemRepository {
    fun getByCategoryId(categoryId: CategoryId): Either<ApiException, Items>
    fun createItem(item: Item): Either<ApiException, Item>
}