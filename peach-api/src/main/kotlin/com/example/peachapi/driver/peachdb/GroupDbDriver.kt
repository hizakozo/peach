package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.GroupInviteCode
import com.example.peachapi.domain.InviteCode
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.gen.Tables.*
import com.example.peachapi.driver.peachdb.gen.tables.records.GroupsRecord
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.time.LocalDateTime
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
                    .set(GROUPS.GROUP_REMARKS, group.groupRemarks?.value)
                    .set(GROUPS.CREATED_BY, group.createBy.value)
                    .set(GROUPS.CHANGED_BY, group.changedBy.value)
                    .execute()
                context.insertInto(USER_GROUPS)
                    .set(USER_GROUPS.USER_ID, group.createBy.value)
                    .set(USER_GROUPS.GROUP_ID, group.groupId.value)
                    .execute()
            }
        }

    fun getGroup(groupId: GroupId, context: DSLContext): Either<Throwable, GroupDetailRecord?> =
        Either.catch {
            context.select(GROUPS.GROUP_ID, GROUPS.GROUP_NAME, GROUPS.GROUP_REMARKS, GROUPS.CREATED_BY,
                GROUPS.CHANGED_BY, INVITE_GROUP.INVITE_CODE, INVITE_GROUP.TERM_TO, INVITE_GROUP.INVITE_BY)
                .from(GROUPS)
                .leftOuterJoin(INVITE_GROUP).on(GROUPS.GROUP_ID.eq(INVITE_GROUP.GROUP_ID))
                .where(GROUPS.GROUP_ID.eq(groupId.value))
                .fetchOneInto(GroupDetailRecord::class.java)
        }

    suspend fun getUserGroups(groupId: GroupId): Either<Throwable, List<UserGroupRecord>> =
        Either.catch {
            dsl.select(USER_GROUPS.USER_ID, USER_GROUPS.GROUP_ID)
                .from(USER_GROUPS)
                .where(USER_GROUPS.GROUP_ID.eq(groupId.value))
                .fetchInto(UserGroupRecord::class.java)
        }

    fun getGroups(userId: UserId): Either<Throwable, List<GroupRecord>> =
        Either.catch {
            dsl.select(GROUPS.GROUP_ID, GROUPS.GROUP_NAME, GROUPS.GROUP_REMARKS, GROUPS.CREATED_BY, GROUPS.CHANGED_BY)
                .from(GROUPS)
                .leftOuterJoin(DELETE_GROUP).on(GROUPS.GROUP_ID.eq(DELETE_GROUP.GROUP_ID))
                .innerJoin(USER_GROUPS)
                .on(GROUPS.GROUP_ID.eq(USER_GROUPS.GROUP_ID))
                .where(USER_GROUPS.USER_ID.eq(userId.value))
                .and(DELETE_GROUP.GROUP_ID.isNull)
                .fetchInto(GroupRecord::class.java)
        }
    fun existUserGroup(userId: UserId, groupId: GroupId): Either<Throwable, Boolean> =
        Either.catch {
            dsl.fetchExists(
                dsl.selectOne()
                    .from(USER_GROUPS)
                    .where(USER_GROUPS.USER_ID.eq(userId.value).and(USER_GROUPS.GROUP_ID.eq(groupId.value)))
            )
        }
    suspend fun update(groupId: GroupId, groupName: GroupName, groupRemarks: GroupRemarks, changedBy: UserId): Either<Throwable, GroupRecord?> =
        Either.catch {
            dsl.update(GROUPS)
                .set(GROUPS.GROUP_NAME, groupName.value)
                .set(GROUPS.GROUP_REMARKS, groupRemarks.value)
                .set(GROUPS.CHANGED_BY, changedBy.value)
                .where(GROUPS.GROUP_ID.eq(groupId.value))
                .returning().fetchOne()?.toRecord()
        }
    fun delete(groupId: GroupId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.delete(GROUPS)
                .where(GROUPS.GROUP_ID.eq(groupId.value))
                .execute()
        }
    fun existsInviteCode(inviteCode: InviteCode): Either<Throwable, Boolean> =
        Either.catch {
            dsl.fetchExists(
                dsl.selectOne()
                    .from(INVITE_GROUP)
                    .where(INVITE_GROUP.INVITE_CODE.eq(inviteCode.value))
            )
        }
    fun createInviteGroup(groupInviteCode: GroupInviteCode, userId: UserId): Either<Throwable, Unit> =
        Either.catch {
            dsl.insertInto(INVITE_GROUP)
                .set(INVITE_GROUP.GROUP_ID, groupInviteCode.groupId.value)
                .set(INVITE_GROUP.INVITE_CODE, groupInviteCode.inviteCode.value)
                .set(INVITE_GROUP.INVITE_BY, userId.value)
                .set(INVITE_GROUP.TERM_TO, groupInviteCode.termTo.value)
                .execute()
        }
    fun deleteInviteGroup(groupId: GroupId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.delete(INVITE_GROUP)
                .where(INVITE_GROUP.GROUP_ID.eq(groupId.value))
                .execute()
        }
    fun deleteUserGroupByGroupId(groupId: GroupId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.delete(USER_GROUPS)
                .where(USER_GROUPS.GROUP_ID.eq(groupId.value))
                .execute()
        }
    fun createUserGroups(userId: UserId, groupId: GroupId, context: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            context.insertInto(USER_GROUPS)
                .set(USER_GROUPS.USER_ID, userId.value)
                .set(USER_GROUPS.GROUP_ID, groupId.value)
                .execute()
        }
    fun deleteUserGroups(userId: UserId, groupId: GroupId): Either<Throwable, Unit> =
        Either.catch {
            dsl.delete(USER_GROUPS)
                .where(USER_GROUPS.USER_ID.eq(userId.value))
                .and(USER_GROUPS.GROUP_ID.eq(groupId.value))
                .execute()
        }
    fun fetchGroupInviteCode(groupId: GroupId): Either<Throwable, InviteGroupRecord?> =
        Either.catch {
            dsl.select(INVITE_GROUP.GROUP_ID, INVITE_GROUP.INVITE_CODE, INVITE_GROUP.TERM_TO, INVITE_GROUP.INVITE_BY)
                .from(INVITE_GROUP)
                .where(INVITE_GROUP.GROUP_ID.eq(groupId.value))
                .fetchOneInto(InviteGroupRecord::class.java)
        }
    private fun GroupsRecord.toRecord() =
        GroupRecord(
            this.groupId, this.groupName, this.groupRemarks, this.createdBy, this.changedBy
        )
    suspend fun getGroupByInviteCode(inviteCode: InviteCode, context: DSLContext): Either<Throwable, GroupDetailRecord?> =
        Either.catch {
            context.select(GROUPS.GROUP_ID, GROUPS.GROUP_NAME, GROUPS.GROUP_REMARKS, GROUPS.CREATED_BY,
                GROUPS.CHANGED_BY, INVITE_GROUP.INVITE_CODE, INVITE_GROUP.TERM_TO, INVITE_GROUP.INVITE_BY)
                .from(GROUPS)
                .leftOuterJoin(INVITE_GROUP).on(GROUPS.GROUP_ID.eq(INVITE_GROUP.GROUP_ID))
                .where(INVITE_GROUP.INVITE_CODE.eq(inviteCode.value))
                .fetchOneInto(GroupDetailRecord::class.java)
        }
}

data class GroupRecord(
    val groupId: UUID,
    val groupName: String,
    val groupRemarks: String?,
    val createdBy: String,
    val changedBy: String
)
data class UserGroupRecord(
    val userId: String,
    val groupId: String
)
data class GroupDetailRecord(
    val groupId: UUID,
    val groupName: String,
    val groupRemarks: String?,
    val createdBy: String,
    val changedBy: String,
    val inviteCode: String?,
    val termTo: LocalDateTime?,
    val inviteBy: String?
)

data class InviteGroupRecord(
    val groupId: String,
    val inviteCode: String,
    val termTo: LocalDateTime,
    val inviteBy: String
)