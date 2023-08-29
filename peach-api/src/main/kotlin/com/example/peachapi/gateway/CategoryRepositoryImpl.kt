package com.example.peachapi.gateway

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.category.*
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.CategoryDbDriver
import com.example.peachapi.driver.peachdb.CategoryRecord
import com.example.peachapi.driver.peachdb.ItemDriver
import com.example.peachapi.driver.peachdb.StatusDriver
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryImpl(private val dbDriver: CategoryDbDriver, private val itemDriver: ItemDriver, private val statusDriver: StatusDriver, private val dslContext: DSLContext): CategoryRepository {
    override fun create(category: Category): Either<UnExpectError, Category> =
        dbDriver.create(category)
            .toUnExpectError()
            .map { category }

    override fun getCategories(groupId: GroupId, userId: UserId): Either<UnExpectError, Categories> =
        dbDriver.getCategories(groupId, userId)
            .toUnExpectError()
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

    override suspend fun existsByUserID(userId: UserId, categoryId: CategoryId): Either<UnExpectError, Boolean> =
        dbDriver.existByUserId(userId, categoryId)
            .toUnExpectError()

    override suspend fun existByStatusId(userId: UserId, statusId: StatusId): Either<UnExpectError, Boolean> =
        dbDriver.existByStatusId(userId, statusId)
            .toUnExpectError()

    override suspend fun delete(categoryId: CategoryId, userId: UserId): Either<UnExpectError, CategoryId> =
        dslContext.transactionResult { config ->
            val context = DSL.using(config)
            itemDriver.deleteAssignedStatusByCategoryIds(listOf(categoryId.value), context)
                .flatMap {
                    statusDriver.deleteByCategoryIds(listOf(categoryId.value), context)
                    itemDriver.deleteByCategoryIds(listOf(categoryId.value), context)
                    dbDriver.delete(listOf(categoryId.value), context)
                }
                .toUnExpectError()
                .map { categoryId }
        }

    override suspend fun update(
        categoryId: CategoryId,
        categoryName: CategoryName,
        categoryRemarks: CategoryRemarks,
        changedBy: UserId
    ): Either<UnExpectError, Category> =
        dbDriver.update(categoryId, categoryName, categoryRemarks, changedBy).toUnExpectError()
            .map { record -> record!!.toCategory() }
    private fun CategoryRecord.toCategory() =
        Category.of(
            this.categoryId,
            this.groupId,
            this.categoryName,
            this.categoryRemarks,
            UserId(this.createdBy),
            UserId(this.changedBy)
        )
}