package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.group.Group
import com.example.peachapi.domain.group.UserGroup
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.gen.Tables.*
import com.example.peachapi.driver.peachdb.gen.tables.GoogleAuthentications
import com.example.peachapi.driver.peachdb.gen.tables.Groups
import com.example.peachapi.driver.peachdb.gen.tables.Users
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.util.UUID
@Component
class GroupDbDriver(private val dsl: DSLContext) {
    fun create(group: Group, dsl: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            dsl.transactionResult { config ->
                val context = DSL.using(config)
                context.insertInto(GROUPS)
                    .set(GROUPS.GROUP_ID, group.groupId.value)
                    .set(GROUPS.GROUP_NAME, group.groupName.value)
                    .set(GROUPS.GROUP_REMARKS, group.groupRemarks.value)
                    .set(GROUPS.CREATED_BY, group.createBy.value)
                    .set(GROUPS.CHANGED_BY, group.changedBy.value)
                    .execute()
                context.insertInto(USER_GROUPS)
                    .set(USER_GROUPS.USER_ID, group.createBy.value)
                    .set(USER_GROUPS.GROUP_ID, group.groupId.value)
                    .execute()
            }
        }

    fun getGroups(userId: UserId): Either<Throwable, List<GroupRecord>> =
        Either.catch {
            dsl.select(GROUPS.GROUP_ID, GROUPS.GROUP_NAME, GROUPS.GROUP_REMARKS, GROUPS.CREATED_BY, GROUPS.CHANGED_BY)
                .from(GROUPS)
                .innerJoin(USER_GROUPS)
                .on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                .where(USER_GROUPS.USER_ID.eq(userId.value))
                .fetchInto(GroupRecord::class.java)
        }
}

data class GroupRecord(
    val groupId: UUID,
    val groupName: String,
    val groupRemarks: String,
    val createdBy: UUID,
    val changedBy: UUID
)