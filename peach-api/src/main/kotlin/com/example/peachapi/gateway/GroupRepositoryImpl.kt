package com.example.peachapi.gateway

import arrow.core.*
import arrow.core.computations.ResultEffect.bind
import arrow.core.computations.either
import arrow.fx.coroutines.parZip
import com.example.peachapi.domain.*
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName
import com.example.peachapi.driver.peachdb.*
import com.example.peachapi.driver.peachdb.fireBase.FirebaseAuthDriver
import com.google.protobuf.Api
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GroupRepositoryImpl(
    private val dbDriver: GroupDbDriver,
    private val itemDriver: ItemDriver,
    private val statusDriver: StatusDriver,
    private val categoryDbDriver: CategoryDbDriver,
    private val dsl: DSLContext,
    private val firebaseAuthDriver: FirebaseAuthDriver
) : GroupRepository {
    override fun create(group: Group): Either<UnExpectError, Unit> =
        dbDriver.create(group, dsl)
            .toUnExpectError()

    override fun getGroups(userId: UserId): Either<UnExpectError, Groups> =
        dbDriver.getGroups(userId)
            .toUnExpectError()
            .map { it.toGroups() }

    override suspend fun getGroup(groupId: GroupId): Either<UnExpectError, Pair<Group?, UserGroups>> =
        either {
            parZip(
                { dbDriver.getGroup(groupId, dsl).bind()?.toGroup() },
                { dbDriver.getUserGroups(groupId).bind() }
            ) { group, userGroupRecords ->
                val userGroups = UserGroups(
                    userGroupRecords.map {
                        val userName = firebaseAuthDriver.getUserRecordByUID(UserId(it.userId)).bind().displayName
                        UserGroup(
                            GroupId(UUID.fromString(it.groupId)),
                            UserId(it.userId),
                            UserName(userName)
                        )
                    }
                )
                Pair(group, userGroups)
            }
        }


    override fun existsUserGroup(userId: UserId, groupId: GroupId): Either<UnExpectError, Boolean> =
        dbDriver.existUserGroup(userId, groupId)
            .toUnExpectError()

    override suspend fun update(
        groupId: GroupId,
        groupName: GroupName,
        groupRemarks: GroupRemarks,
        changedBy: UserId
    ): Either<UnExpectError, Group> =
        dbDriver.update(groupId, groupName, groupRemarks, changedBy)
            .toUnExpectError()
            .map { it!!.toGroup() }

    override suspend fun delete(groupId: GroupId, userId: UserId): Either<UnExpectError, GroupId> =
        // assin status, status, item, category, group
        dsl.transactionResult { config ->
            val context = DSL.using(config)
            categoryDbDriver.getCategories(groupId, userId)
                .flatMap {
                    val categoryIds = it.map { c -> c.categoryId }
                    itemDriver.deleteAssignedStatusByCategoryIds(categoryIds, context)
                    statusDriver.deleteByCategoryIds(categoryIds, context)
                    itemDriver.deleteByCategoryIds(categoryIds, context)
                    categoryDbDriver.delete(categoryIds, context)
                    dbDriver.deleteInviteGroup(groupId, context)
                    dbDriver.deleteUserGroupByGroupId(groupId, context)
                    dbDriver.delete(groupId, context)
                }
                .toUnExpectError()
                .map { groupId }
        }

    override suspend fun existsInviteCode(inviteCode: InviteCode): Either<UnExpectError, Boolean> =
        dbDriver.existsInviteCode(inviteCode).toUnExpectError()

    override suspend fun createInviteCode(
        groupInviteCode: GroupInviteCode,
        userId: UserId
    ): Either<UnExpectError, GroupInviteCode> =
        dsl.transactionResult { config ->
            val context = DSL.using(config)
            dbDriver.deleteInviteGroup(groupInviteCode.groupId, context)
            dbDriver.createInviteGroup(groupInviteCode, userId)
        }.toUnExpectError()
            .map { groupInviteCode }

    override suspend fun fetchGroupByInviteCode(inviteCode: InviteCode): Either<UnExpectError, Group?> =
        dbDriver.getGroupByInviteCode(inviteCode, dsl).toUnExpectError().map { it?.toGroup() }

    override suspend fun createUserGroups(
        userId: UserId,
        groupId: GroupId
    ): Either<UnExpectError, Group> =
        dsl.transactionResult { config ->
            val context = DSL.using(config)
            dbDriver.createUserGroups(userId, groupId, context)
            dbDriver.getGroup(groupId, context)
        }.toUnExpectError()
            .flatMap {
                it?.toGroup()?.right() ?: UnExpectError(null, "").left()
            }
    override suspend fun deleteUserGroups(userId: UserId, groupId: GroupId): Either<UnExpectError, GroupId> =
        dbDriver.deleteUserGroups(userId, groupId).toUnExpectError().map { groupId }

    private fun List<GroupRecord>.toGroups(): Groups =
        Groups(
            this.map { it.toGroup() }
        )

    private fun GroupRecord.toGroup(): Group =
        Group(
            GroupId(this.groupId),
            GroupName(this.groupName),
            if (this.groupRemarks == null) null else GroupRemarks(this.groupRemarks),
            UserId(this.createdBy),
            UserId(this.changedBy),
            null
        )

    private fun GroupDetailRecord.toGroup(): Group =
        Group(
            GroupId(this.groupId),
            GroupName(this.groupName),
            if (this.groupRemarks == null) null else GroupRemarks(this.groupRemarks),
            UserId(this.createdBy),
            UserId(this.changedBy),
            if (this.inviteCode == null) null else
            GroupInviteCode(
                GroupId(this.groupId),
                InviteCode(this.inviteCode),
                PeachDateTime(this.termTo!!),
                UserId(this.inviteBy!!)
            )
        )

}