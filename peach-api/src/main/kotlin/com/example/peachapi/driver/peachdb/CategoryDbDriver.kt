package com.example.peachapi.driver.peachdb

import arrow.core.Either
import arrow.core.computations.either
import com.example.peachapi.domain.category.*
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.gen.Tables
import com.example.peachapi.driver.peachdb.gen.Tables.DELETE_CATEGORY
import com.example.peachapi.driver.peachdb.gen.tables.Categories.CATEGORIES
import com.example.peachapi.driver.peachdb.gen.tables.Groups.GROUPS
import com.example.peachapi.driver.peachdb.gen.tables.UserGroups.USER_GROUPS
import com.example.peachapi.driver.peachdb.gen.tables.Statues.STATUES
import com.example.peachapi.driver.peachdb.gen.tables.records.CategoriesRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CategoryDbDriver(private val dsl: DSLContext) {

    fun create(category: Category): Either<Throwable, Unit> =
        Either.catch {
            dsl.insertInto(CATEGORIES)
                .set(CATEGORIES.CATEGORY_ID, category.categoryId.value)
                .set(CATEGORIES.GROUP_ID, category.groupId.value)
                .set(CATEGORIES.CATEGORY_NAME, category.categoryName.value)
                .set(CATEGORIES.CATEGORY_REMARKS, category.categoryRemarks.value)
                .set(CATEGORIES.CREATED_BY, category.createBy.value)
                .set(CATEGORIES.CHANGED_BY, category.changedBy.value)
                .execute()
        }

    fun getCategories(groupId: GroupId, userId: UserId): Either<Throwable, List<CategoryRecord>> =
        Either.catch {
            dsl.select(
                CATEGORIES.CATEGORY_ID, CATEGORIES.GROUP_ID, CATEGORIES.CATEGORY_NAME,
                CATEGORIES.CATEGORY_REMARKS,
                CATEGORIES.CREATED_BY, CATEGORIES.CHANGED_BY
            )
                .from(CATEGORIES)
                .innerJoin(GROUPS)
                .on(CATEGORIES.GROUP_ID.eq(GROUPS.GROUP_ID))
                .innerJoin(USER_GROUPS)
                .on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                .where(CATEGORIES.GROUP_ID.eq(groupId.value).and(USER_GROUPS.USER_ID.eq(userId.value)))
                .fetchInto(CategoryRecord::class.java)
        }
    suspend fun existByUserId(userId: UserId, categoryId: CategoryId): Either<Throwable, Boolean> =
        Either.catch {
            dsl.fetchExists(
                dsl.selectOne()
                    .from(CATEGORIES)
                    .innerJoin(GROUPS).on(CATEGORIES.GROUP_ID.eq(GROUPS.GROUP_ID))
                    .innerJoin(USER_GROUPS).on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                    .where(USER_GROUPS.USER_ID.eq(userId.value).and(CATEGORIES.CATEGORY_ID.eq(categoryId.value)))
            )
        }
    suspend fun existByStatusId(userId: UserId, statusId: StatusId): Either<Throwable, Boolean> =
        Either.catch {
            dsl.fetchExists(
                dsl.selectOne()
                    .from(CATEGORIES)
                    .innerJoin(STATUES).on(CATEGORIES.CATEGORY_ID.eq(STATUES.CATEGORY_ID))
                    .innerJoin(GROUPS).on(CATEGORIES.GROUP_ID.eq(GROUPS.GROUP_ID))
                    .innerJoin(USER_GROUPS).on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                    .where(USER_GROUPS.USER_ID.eq(userId.value).and(STATUES.STATUS_ID.eq(statusId.value)))
            )
        }

    suspend fun update(categoryId: CategoryId, categoryName: CategoryName, categoryRemarks: CategoryRemarks, changedBy: UserId): Either<Throwable, CategoryRecord?> =
        Either.catch {
            dsl.update(CATEGORIES)
                .set(CATEGORIES.CATEGORY_NAME, categoryName.value)
                .set(CATEGORIES.CATEGORY_REMARKS, categoryRemarks.value)
                .set(CATEGORIES.CHANGED_BY, changedBy.value)
                .where(CATEGORIES.CATEGORY_ID.eq(categoryId.value))
                .returning().fetchOne()?.toRecord()
        }
    fun delete(categoryIds: List<UUID>, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.delete(CATEGORIES)
                .where(CATEGORIES.CATEGORY_ID.`in`(categoryIds))
                .execute()
        }
    private fun CategoriesRecord.toRecord() =
        CategoryRecord(
            this.categoryId, this.groupId, this.categoryName, this.categoryRemarks, this.createdBy, this.changedBy
        )
}

data class CategoryRecord(
    val categoryId: UUID,
    val groupId: UUID,
    val categoryName: String,
    val categoryRemarks: String,
    val createdBy: String,
    val changedBy: String
)