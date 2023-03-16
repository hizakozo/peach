package com.example.peachapi.gateway

import arrow.core.Either
import arrow.core.computations.ResultEffect.bind
import arrow.core.flatMap
import com.example.peachapi.domain.*
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName
import com.example.peachapi.driver.peachdb.GroupDbDriver
import com.example.peachapi.driver.peachdb.GroupRecord
import com.example.peachapi.driver.peachdb.fireBase.FirebaseAuthDriver
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GroupRepositoryImpl(
    private val dbDriver: GroupDbDriver,
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
        dbDriver.delete(groupId, userId)
            .toUnExpectError()
            .map { GroupId(it!!) }

    override suspend fun existsInviteCode(inviteCode: InviteCode): Either<UnExpectError, Boolean> =
        dbDriver.existsInviteCode(inviteCode).toUnExpectError()

    override suspend fun createInviteCode(
        groupInviteCode: GroupInviteCode,
        userId: UserId
    ): Either<UnExpectError, Pair<GroupInviteCode, UserName>> =
        dbDriver.deleteInviteGroup(groupInviteCode.groupId).toUnExpectError()
            .flatMap {
                dbDriver.createInviteGroup(groupInviteCode, userId).toUnExpectError().map {
                    val userName = UserName(
                        firebaseAuthDriver.getUserRecordByUID(groupInviteCode.inviteBy).map { it.displayName }.bind()
                    )
                    Pair(groupInviteCode, userName)
                }
            }

    override suspend fun fetchGroupInviteCode(groupId: GroupId): Either<UnExpectError, Pair<GroupInviteCode, UserName>?> =
        dbDriver.fetchGroupInviteCode(groupId).toUnExpectError().flatMap { inviteGroupRecord ->
            if (inviteGroupRecord == null) {
                Either.Right(null)
            }
            val userName = UserName(
                firebaseAuthDriver.getUserRecordByUID(UserId(inviteGroupRecord!!.inviteBy)).bind().displayName
            )
            val gc = GroupInviteCode(
                GroupId(UUID.fromString(inviteGroupRecord.groupId)),
                InviteCode(inviteGroupRecord.inviteCode),
                PeachDateTime(inviteGroupRecord.termTo),
                UserId(inviteGroupRecord.inviteBy)
            )
            Either.Right(Pair(gc, userName))
        }

    override suspend fun createUserGroups(
        userId: UserId,
        groupId: GroupId
    ): Either<UnExpectError, Pair<UserId, GroupId>> =
        dbDriver.createUserGroups(userId, groupId).toUnExpectError().map { Pair(userId, groupId) }

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
            UserId(this.changedBy)
        )
}