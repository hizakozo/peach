package com.example.peachapi.driver.peachdb

import arrow.core.Either
import arrow.core.computations.either
import com.example.peachapi.domain.category.Categories
import com.example.peachapi.domain.category.Category
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.gen.Tables
import com.example.peachapi.driver.peachdb.gen.tables.Categories.CATEGORIES
import com.example.peachapi.driver.peachdb.gen.tables.Groups.GROUPS
import com.example.peachapi.driver.peachdb.gen.tables.UserGroups.USER_GROUPS
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

    fun getCategories(groupId: GroupId, userId: UserId): Either<Throwable, List<CategoriesRecord>> =
        Either.catch {
            dsl.select(
                CATEGORIES.CATEGORY_ID, CATEGORIES.CATEGORY_NAME,
                CATEGORIES.CATEGORY_REMARKS,
                CATEGORIES.CREATED_BY, CATEGORIES.CHANGED_BY
            )
                .from(CATEGORIES)
                .innerJoin(GROUPS)
                .on(CATEGORIES.GROUP_ID.eq(GROUPS.GROUP_ID))
                .innerJoin(USER_GROUPS)
                .on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                .where(CATEGORIES.GROUP_ID.eq(groupId.value).and(USER_GROUPS.USER_ID.eq(userId.value)))
                .fetchInto(CategoriesRecord::class.java)
        }
    fun existByUserId(userId: UserId, categoryId: CategoryId): Either<Throwable, Boolean> =
        Either.catch {
            dsl.fetchExists(
                dsl.selectOne()
                    .from(CATEGORIES)
                    .innerJoin(GROUPS).on(CATEGORIES.GROUP_ID.eq(GROUPS.GROUP_ID))
                    .innerJoin(USER_GROUPS).on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                    .where(USER_GROUPS.USER_ID.eq(userId.value).and(CATEGORIES.CATEGORY_ID.eq(categoryId.value)))
            )
        }
}

data class CategoriesRecord(
    val categoryId: UUID,
    val categoryName: String,
    val categoryRemarks: String,
    val createdBy: String,
    val changedBy: String
)