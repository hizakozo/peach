package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.NotFoundDataException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.Category
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.CategoryDbDriver
import org.jooq.User
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryImpl(private val dbDriver: CategoryDbDriver): CategoryRepository {
    override fun create(category: Category): Either<ApiException, Category> =
        dbDriver.create(category)
            .mapLeft { UnExpectError(it, it.message) }
            .map { category }

    override fun getCategories(groupId: GroupId, userId: UserId): Either<ApiException, Categories> =
        dbDriver.getCategories(groupId, userId)
            .mapLeft { UnExpectError(it, it.message) }
            .map { Categories(
                it.map { record ->
                    Category.of(
                        record.categoryId,
                        groupId.value,
                        record.categoryName,
                        record.categoryRemarks,
                        UserId(record.createdBy),
                        UserId(record.changedBy)
                    )
                }
            ) }
}